package com.EasyTravel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.config.ApiKeyConfig;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final OpenAiService openAiService;
    private final String gptModel;
    private final int gptTimeout;

    @Autowired
    public ChatbotController(ApiKeyConfig apiKeyConfig) {
        // application-secret.yml에서 설정값들을 읽어옴
        String apiKey = apiKeyConfig.getOpenaiKey();
        this.gptModel = "gpt-4o-mini"; // 또는 설정 파일에서 읽어올 수 있음
        this.gptTimeout = 60;

        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(gptTimeout));

        // 로그 출력 (개발용)
        System.out.println("ChatbotController 초기화됨:");
        System.out.println("- GPT 모델: " + gptModel);
        System.out.println("- 타임아웃: " + gptTimeout + "초");
        System.out.println("- API 키 설정됨: " + (apiKey != null && !apiKey.isEmpty()));
    }

    @GetMapping
    public String chatbotPage() {
        return "chatbot"; // chatbot.html 페이지를 반환
    }

    @PostMapping("/send")
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> requestData, HttpSession session) {
        String userMessage = requestData.get("message");

        Map<String, Object> response = new HashMap<>();

        try {
            // 세션에서 대화 기록 가져오기 (없으면 새로 생성)
            @SuppressWarnings("unchecked")
            List<ChatMessage> conversationHistory = (List<ChatMessage>) session.getAttribute("chatHistory");
            if (conversationHistory == null) {
                conversationHistory = new ArrayList<>();
                // 시스템 메시지 추가 (챗봇의 역할 정의)
                conversationHistory.add(new ChatMessage("system",
                        "당신은 EasyTravel의 친근한 여행 도우미 챗봇입니다. " +
                                "사용자의 여행 관련 질문에 도움이 되고 유익한 답변을 제공하세요. " +
                                "여행 계획, 관광지 정보, 교통편, 숙박, 맛집 등 여행과 관련된 모든 질문에 답할 수 있습니다. " +
                                "답변은 한국어로 친근하고 자연스럽게 해주세요."
                ));
            }

            // 사용자 메시지 추가
            conversationHistory.add(new ChatMessage("user", userMessage));

            // 대화 기록이 너무 길어지면 오래된 메시지 제거 (시스템 메시지는 유지)
            if (conversationHistory.size() > 21) { // 시스템 메시지 1개 + 대화 20개
                List<ChatMessage> trimmedHistory = new ArrayList<>();
                trimmedHistory.add(conversationHistory.get(0)); // 시스템 메시지 유지
                trimmedHistory.addAll(conversationHistory.subList(conversationHistory.size() - 20, conversationHistory.size()));
                conversationHistory = trimmedHistory;
            }

            // ChatGPT API 호출
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(gptModel)
                    .messages(conversationHistory)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();

            if (!choices.isEmpty()) {
                String botResponse = choices.get(0).getMessage().getContent();

                // 봇 응답을 대화 기록에 추가
                conversationHistory.add(new ChatMessage("assistant", botResponse));

                // 세션에 업데이트된 대화 기록 저장
                session.setAttribute("chatHistory", conversationHistory);

                response.put("success", true);
                response.put("message", botResponse);
            } else {
                response.put("success", false);
                response.put("message", "죄송합니다. 응답을 생성하지 못했습니다. 다시 시도해주세요.");
            }

        } catch (Exception e) {
            System.err.println("ChatGPT API 오류: " + e.getMessage());
            e.printStackTrace();

            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }

        return response;
    }

    @PostMapping("/clear")
    public Map<String, Object> clearChat(HttpSession session) {
        session.removeAttribute("chatHistory");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "채팅 기록이 초기화되었습니다.");

        return response;
    }

    @GetMapping("/history")
    public Map<String, Object> getChatHistory(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ChatMessage> conversationHistory = (List<ChatMessage>) session.getAttribute("chatHistory");

        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();

        if (conversationHistory != null) {
            for (ChatMessage msg : conversationHistory) {
                if (!"system".equals(msg.getRole())) { // 시스템 메시지는 제외
                    Map<String, String> messageData = new HashMap<>();
                    messageData.put("role", msg.getRole());
                    messageData.put("content", msg.getContent());
                    messages.add(messageData);
                }
            }
        }

        response.put("success", true);
        response.put("messages", messages);

        return response;
    }
}