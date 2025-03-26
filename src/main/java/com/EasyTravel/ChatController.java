package com.EasyTravel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

	@RestController
	@RequestMapping("/api/chat")
	public class ChatController {

    private final OpenAiService openAiService;
    
    @Value("${api.google-key}")
    private String googleApiKey;
    
    @Value("${api.openai-key}")
    private String openAiKey;
    
    private final String googleGeocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=" + googleApiKey;

    public ChatController() {
        String apiKey = openAiKey;

        // 🔥 타임아웃을 60초로 설정하여 Timeout 오류 방지
        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(60));
    }

    @PostMapping
    public String chat(@RequestBody String userMessage) {
        List<String> suggestedLocations = getSuggestedLocations(userMessage);
        System.out.println("GPT가 생성한 장소 리스트: " + suggestedLocations);

        List<String> validLocations = validateLocations(new ArrayList<>(suggestedLocations));
        System.out.println("Google Geocoding API 검증 장소 리스트: " + validLocations);

        if (validLocations.isEmpty()) {
            return "<p style='color:red; font-weight:bold;'>⚠️ 존재하는 장소가 없습니다. 다른 요청을 입력하세요.</p>";
        }

        String finalPlan = generateItinerary(userMessage, validLocations);
        return formatResponse(validLocations, finalPlan);
    }

    private List<String> getSuggestedLocations(String userMessage) {
        List<ChatMessage> messages = Collections.singletonList(
            new ChatMessage("user", userMessage + " 방문할 장소 리스트만 출력해줘. 설명 없이 장소명만 줄바꿈으로 나열해.")
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(messages)
            .maxTokens(600)
            .temperature(0.7)
            .build();

        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        if (choices.isEmpty()) return Collections.emptyList();

        return List.of(choices.get(0).getMessage().getContent().trim().split("\n")).stream()
            .map(String::trim)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> validateLocations(List<String> locations) {
        RestTemplate restTemplate = new RestTemplate();
        List<String> verifiedLocations = new ArrayList<>();

        for (String location : locations) {
            System.out.println("검증 중: " + location);
            String url = String.format(googleGeocodeUrl, location);
            try {
                String response = restTemplate.getForObject(url, String.class);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray results = jsonResponse.optJSONArray("results");

                if (results != null && results.length() > 0) {
                    System.out.println("결과: ✅ 존재함");
                    verifiedLocations.add(location);
                } else {
                    System.out.println("결과: ❌ 존재하지 않음");
                }
            } catch (Exception e) {
                System.out.println("❌ Google Geocoding API 오류: " + e.getMessage());
            }
        }
        return verifiedLocations;
    }

    private String generateItinerary(String userMessage, List<String> validLocations) {
        String locationList = String.join(", ", validLocations);
        String finalPrompt = userMessage + "계획짤때 다음 장소 중 시간에 맞게 장소를 몇개 선택해서 여행 계획을 작성해줘: " + locationList;

        List<ChatMessage> messages = Collections.singletonList(new ChatMessage("user", finalPrompt));

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(messages)
            .maxTokens(1500)
            .temperature(0.7)
            .build();

        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        if (choices.isEmpty()) return "No response from GPT";

        return choices.get(0).getMessage().getContent().trim();
    }

    private String formatResponse(List<String> validLocations, String finalPlan) {
        return "<p><strong>🔍 검증된 장소:</strong><br>" + String.join(", ", validLocations) + "</p>" +
               "<p><strong>📌 GPT 여행 계획:</strong><br>" + finalPlan.replace("\n", "<br>") + "</p>";
    }
}
