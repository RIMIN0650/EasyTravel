package com.EasyTravel;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.EasyTravel.preference.domain.Preference;
import com.EasyTravel.preference.service.PreferenceService;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OpenAiService openAiService;
    private final String googleApiKey;
    private final String weatherApiKey;
    private final String googleGeocodeUrl;
    private final String weatherApiUrl;
    private final String gptModel;
    private final String gptItineraryModel;

    @Autowired
    private PreferenceService preferenceService;

    public ChatController(
            @Value("${api.google-key}") String googleKey,
            @Value("${api.weather-key}") String weatherKey,
            @Value("${gpt.api.key}") String openaiKey,
            @Value("${gpt.api.model}") String model,
            @Value("${gpt.api.itinerary-model}") String itineraryModel,
            @Value("${gpt.api.timeout}") int timeout,
            @Value("${external.google.geocode-url}") String geocodeUrl,
            @Value("${external.weather.forecast-url}") String forecastUrl) {

        // OpenAI 서비스 초기화
        this.openAiService = new OpenAiService(openaiKey, Duration.ofSeconds(timeout));

        // API 키들 설정
        this.googleApiKey = googleKey;
        this.weatherApiKey = weatherKey;

        // GPT 모델 설정
        this.gptModel = model;
        this.gptItineraryModel = itineraryModel;

        // API URL 설정
        this.googleGeocodeUrl = String.format(geocodeUrl, "%s", googleKey);
        this.weatherApiUrl = String.format(forecastUrl, "%s", "%s", weatherKey);

        // 초기화 로그
        System.out.println("ChatController 초기화됨:");
        System.out.println("- GPT 기본 모델: " + model);
        System.out.println("- GPT 일정 생성 모델: " + itineraryModel);
        System.out.println("- API 타임아웃: " + timeout + "초");
        System.out.println("- Google API 키 설정: " + (googleKey != null && !googleKey.isEmpty()));
        System.out.println("- Weather API 키 설정: " + (weatherKey != null && !weatherKey.isEmpty()));
        System.out.println("- OpenAI API 키 설정: " + (openaiKey != null && !openaiKey.isEmpty()));
    }

    @PostMapping
    public String chat(@RequestBody Map<String, String> requestData, HttpSession session) {
        String start = requestData.get("start");
        String destination = requestData.get("destination");
        String days = requestData.get("days");
        String theme = requestData.get("theme");
        String startDate = requestData.get("startDate");

        LocalDate parsedStartDate = null;
        if(startDate != null && !startDate.isEmpty()) {
            try {
                parsedStartDate = LocalDate.parse(startDate);
            } catch(Exception e) {
                System.out.println("날짜 파싱 오류: " + e.getMessage());
            }
        }

        int id = (Integer)session.getAttribute("userId");
        Preference preference = preferenceService.findPreference(id);

        String userMessage = String.format(
                "출발지는 %s, 목적지는 %s, 여행 기간은 %s일, 테마는 %s 이야. 이에 맞는 여행 장소 리스트를 출발지와 목적지 사이에 가능한 균등하게 분배되게 추천해줘. " +
                        "사용자 취향은 첫 번째: %s, 두 번째: %s, 세 번째: %s, 기타: %s 이야.",
                start, destination, days, theme,
                preference.getFirst(), preference.getSecond(), preference.getThird(), preference.getEtcetra()
        );

        System.out.println("📝 생성된 userMessage: " + userMessage);

        List<String> suggestedLocations = getSuggestedLocations(userMessage);
        List<Map<String, Object>> validLocationsWithCoords = validateAndGetCoordinates(suggestedLocations);

        if (validLocationsWithCoords.isEmpty()) {
            return "<p style='color:red; font-weight:bold;'>⚠️ 존재하는 장소가 없습니다. 다른 요청을 입력하세요.</p>";
        }

        List<String> validLocationNames = validLocationsWithCoords.stream()
                .map(loc -> (String) loc.get("name"))
                .collect(Collectors.toList());

        String htmlPlan = generateItinerary(userMessage, validLocationNames, parsedStartDate);

        // 실제 일정에 사용된 장소 추출
        List<String> usedPlaceNames = extractUsedPlaces(htmlPlan);

        // 일정에서 장소와 날짜/시간 정보 추출
        Map<String, Map<String, Object>> locationSchedules = extractLocationSchedules(htmlPlan, parsedStartDate);

        List<Map<String, Object>> usedValidLocations = validLocationsWithCoords.stream()
                .filter(loc -> usedPlaceNames.contains((String) loc.get("name")))
                .collect(Collectors.toList());

        // 각 장소에 일정 정보 추가
        for (Map<String, Object> location : usedValidLocations) {
            String placeName = (String) location.get("name");
            Map<String, Object> scheduleInfo = locationSchedules.get(placeName);
            if (scheduleInfo != null) {
                location.put("visit_date", scheduleInfo.get("date"));
                location.put("visit_time", scheduleInfo.get("time"));

                // 날씨 정보 가져오기
                if (parsedStartDate != null) {
                    try {
                        Double lat = (Double) location.get("lat");
                        Double lng = (Double) location.get("lng");
                        String visitDate = (String) scheduleInfo.get("date");
                        String visitTime = (String) scheduleInfo.get("time");

                        Map<String, Object> weatherData = getWeatherForecast(lat, lng, visitDate, visitTime);
                        if (weatherData != null) {
                            location.put("weather", weatherData);
                        }
                    } catch (Exception e) {
                        System.out.println("날씨 정보 가져오기 오류: " + e.getMessage());
                    }
                }
            }
        }

        // 사용된 장소만 지도 마커로 표시 (날씨 정보 포함)
        String mapData = buildMapData(usedValidLocations);
        return htmlPlan + mapData;
    }

    // 각 장소의 방문 날짜와 시간 정보 추출
    private Map<String, Map<String, Object>> extractLocationSchedules(String html, LocalDate startDate) {
        Map<String, Map<String, Object>> schedules = new HashMap<>();

        if (startDate == null) return schedules;

        System.out.println("시작 날짜: " + startDate);

        Pattern dayPattern = Pattern.compile("<div class=\"day-card\">(.*?)(?=<div class=\"day-card\"|$)", Pattern.DOTALL);
        Matcher dayMatcher = dayPattern.matcher(html);

        int dayCount = 0;
        while (dayMatcher.find()) {
            dayCount++;
            LocalDate currentDate = startDate.plusDays(dayCount - 1);
            String dayContent = dayMatcher.group(1);

            System.out.println("일차: " + dayCount + ", 날짜: " + currentDate);

            Pattern entryPattern = Pattern.compile("<div class=\"entry\">(.*?)</div>", Pattern.DOTALL);
            Matcher entryMatcher = entryPattern.matcher(dayContent);

            while (entryMatcher.find()) {
                String entry = entryMatcher.group(1);

                Pattern timePattern = Pattern.compile("<strong>시간:</strong>\\s*<span>(.*?)</span>");
                Matcher timeMatcher = timePattern.matcher(entry);

                Pattern placePattern = Pattern.compile("<strong>장소:</strong>\\s*<span>(.*?)</span>");
                Matcher placeMatcher = placePattern.matcher(entry);

                if (timeMatcher.find() && placeMatcher.find()) {
                    String time = timeMatcher.group(1).trim();
                    String place = placeMatcher.group(1).trim();

                    Map<String, Object> scheduleInfo = new HashMap<>();
                    scheduleInfo.put("date", currentDate.format(DateTimeFormatter.ISO_DATE));
                    scheduleInfo.put("time", time);
                    scheduleInfo.put("day_count", dayCount);

                    System.out.println("장소: " + place + ", 날짜: " + currentDate.format(DateTimeFormatter.ISO_DATE) + ", 시간: " + time + ", 일차: " + dayCount);

                    schedules.put(place, scheduleInfo);
                }
            }
        }

        return schedules;
    }

    // 날씨 API를 통해 특정 날짜/시간의 날씨 정보 가져오기
    private Map<String, Object> getWeatherForecast(double lat, double lng, String date, String time) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(weatherApiUrl, lat, lng);

            String response = restTemplate.getForObject(url, String.class);

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray forecastList = jsonResponse.getJSONArray("list");

            LocalDate targetDate = LocalDate.parse(date);
            String startTime = time.split("~")[0].trim();
            int targetHour = Integer.parseInt(startTime.split(":")[0]);

            JSONObject closestForecast = null;
            long minTimeDiff = Long.MAX_VALUE;

            for (int i = 0; i < forecastList.length(); i++) {
                JSONObject forecast = forecastList.getJSONObject(i);
                long timestamp = forecast.getLong("dt") * 1000;
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);

                LocalDate fcDate = LocalDate.of(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH)
                );

                int fcHour = cal.get(Calendar.HOUR_OF_DAY);

                long dayDiff = Math.abs(java.time.temporal.ChronoUnit.DAYS.between(targetDate, fcDate));
                long hourDiff = Math.abs(targetHour - fcHour);
                long totalDiff = (dayDiff * 24) + hourDiff;

                if (totalDiff < minTimeDiff) {
                    minTimeDiff = totalDiff;
                    closestForecast = forecast;
                }
            }

            if (closestForecast != null) {
                Map<String, Object> weatherData = new HashMap<>();
                JSONObject main = closestForecast.getJSONObject("main");
                JSONArray weatherArray = closestForecast.getJSONArray("weather");
                JSONObject weatherDetails = weatherArray.getJSONObject(0);

                weatherData.put("temp", main.getDouble("temp"));
                weatherData.put("description", weatherDetails.getString("description"));
                weatherData.put("icon", weatherDetails.getString("icon"));

                return weatherData;
            }
        } catch (Exception e) {
            System.out.println("날씨 정보 조회 오류: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // userMessage에 맞는 여행지 리스트를 추천받음
    private List<String> getSuggestedLocations(String userMessage) {
        ChatMessage chatMessage = new ChatMessage("user", userMessage + " 장소명만 줄바꿈으로 출력해줘.");
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(gptModel)  // 설정 파일에서 가져온 모델 사용
                .messages(Collections.singletonList(chatMessage))
                .maxTokens(600)
                .temperature(0.7)
                .build();

        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        if (choices.isEmpty()) return Collections.emptyList();

        return Arrays.stream(choices.get(0).getMessage().getContent().split("\n"))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> validateAndGetCoordinates(List<String> locations) {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> verifiedLocations = new ArrayList<>();

        for (String location : locations) {
            System.out.println("검증 중: " + location);
            String url = String.format(googleGeocodeUrl, location);
            try {
                String response = restTemplate.getForObject(url, String.class);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray results = jsonResponse.optJSONArray("results");

                if (results != null && results.length() > 0) {
                    JSONObject firstResult = results.getJSONObject(0);
                    JSONObject geometry = firstResult.getJSONObject("geometry");
                    JSONObject location_data = geometry.getJSONObject("location");
                    double lat = location_data.getDouble("lat");
                    double lng = location_data.getDouble("lng");

                    Map<String, Object> locationData = new HashMap<>();
                    locationData.put("name", location);
                    locationData.put("lat", lat);
                    locationData.put("lng", lng);

                    System.out.println("결과: ✅ 존재함 (좌표: " + lat + ", " + lng + ")");
                    verifiedLocations.add(locationData);
                } else {
                    System.out.println("결과: ❌ 존재하지 않음");
                }
            } catch (Exception e) {
                System.out.println("❌ Google Geocoding API 오류: " + e.getMessage());
            }
        }
        return verifiedLocations;
    }

    private String generateItinerary(String userMessage, List<String> validLocations, LocalDate startDate) {
        String locationList = String.join(", ", validLocations);
        String trimmedUserMessage = userMessage.split("이에 맞는")[0].trim();

        String dateInfo = "";
        if (startDate != null) {
            dateInfo = String.format(", 출발 날짜는 %s", startDate.format(DateTimeFormatter.ISO_DATE));
        }

        String finalPrompt = trimmedUserMessage + dateInfo +
                "\n\n다음 여행 장소 리스트 중 시간에 맞게 경유지를 몇개 선택해서 여행 계획을 HTML 웹페이지 형식으로 구성해줘. " +
                "여행 장소 리스트: " + locationList+
                "locationList에서 일정에 포함된 장소들을 locationList의 순서대로 <ul id=\"place-list\" style=\"display:none;\">로 출력해줘. 이 리스트는 지도 마커 용도로만 사용되고, 화면에는 보이지 않아야 해.\n" +
                "다음 조건을 만족하는 일정표를 HTML로 출력해줘:\r\n"+
                "1. 여행 요약: 전체 일정 상단에 1~2문장으로 여행 테마나 특징을 소개하는 요약 문단을 넣어줘.\n" +
                "2. 일정 구성:\n" +
                "- 하루 단위로 `<div class=\"day-card\">`로 묶고, `<h2>`로 날짜/제목을 적어줘.\n" +
                "- 각 일정 항목은 `<div class=\"entry\">`로 묶어 출력해.\n" +
                "- 각 항목은 다음 형식을 반드시 따라:\n\n" +
                "■ 장소 방문 일정:\n" +
                "<div class=\"entry\">\n" +
                "  <strong>시간:</strong> <span>09:00 ~ 10:30</span>\n" +
                "  <strong>장소:</strong> <span>삼락생태공원</span>\n" +
                "  <strong>설명:</strong> <span>강변과 꽃밭이 어우러진 드넓은 공간에서 사진 촬영</span>\n" +
                "  <strong>교통:</strong> <span>이전장소에서 해당 장소로 이동하기 위한 교통수단 설명</span>\n" +
                "</div>\n\n" +
                "■ 식사 일정:\n" +
                "<div class=\"entry\">\n" +
                "  <strong>시간:</strong> <span>12:30 ~ 13:30</span>\n" +
                "  <strong>식사:</strong> <span>광안리 회센터 추천</span>\n" +
                "</div>\n\n" +
                "■ 단순 이동/설명 일정:\n" +
                "<div class=\"entry\">\n" +
                "  <strong>시간:</strong> <span>14:00 ~ 15:00</span>\n" +
                "  <strong>설명:</strong> <span>숙소에서 나와 버스로 이동</span>\n" +
                "</div>\n\n" +
                "■ 숙박 일정:\n" +
                "<div class=\"entry\">\n" +
                "  <strong>시간:</strong> <span>21:00 ~</span>\n" +
                "  <strong>숙박:</strong> <span>해운대 근처 호텔</span>\n" +
                "</div>\n\n" +
                "4.교통은 이전장소에서 해당 장소로 이동하기 위한 구체적인 교통수단과 방법 설명" +
                "5.스타일은 CSS로 나중에 적용할 예정이니, HTML 구조만 일관되게 유지해줘.\n" +
                "6.다른 설명 없이 HTML만 출력해줘.\n\n";

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(gptItineraryModel)  // 일정 생성용 전용 모델 사용
                .messages(Collections.singletonList(new ChatMessage("user", finalPrompt)))
                .maxTokens(2000)
                .temperature(0.7)
                .build();

        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(request).getChoices();
        if (choices.isEmpty()) return "<p>⚠️ 여행 일정을 생성하지 못했습니다.</p>";

        String itineraryHtml = choices.get(0).getMessage().getContent().trim();

        // 마크다운 문법 제거
        if (itineraryHtml.startsWith("```html")) {
            itineraryHtml = itineraryHtml.replaceFirst("```html", "").trim();
        }
        if (itineraryHtml.endsWith("```")) {
            itineraryHtml = itineraryHtml.substring(0, itineraryHtml.lastIndexOf("```")).trim();
        }

        return itineraryHtml;
    }

    private String buildMapData(List<Map<String, Object>> locations) {
        JSONArray mapData = new JSONArray();
        for (Map<String, Object> loc : locations) {
            JSONObject obj = new JSONObject();
            obj.put("name", loc.get("name"));
            obj.put("lat", loc.get("lat"));
            obj.put("lng", loc.get("lng"));

            if (loc.containsKey("visit_date")) {
                obj.put("visit_date", loc.get("visit_date"));
            }
            if (loc.containsKey("visit_time")) {
                obj.put("visit_time", loc.get("visit_time"));
            }

            if (loc.containsKey("weather")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> weatherData = (Map<String, Object>) loc.get("weather");
                JSONObject weather = new JSONObject();
                weather.put("temp", weatherData.get("temp"));
                weather.put("description", weatherData.get("description"));
                weather.put("icon", weatherData.get("icon"));
                obj.put("weather", weather);
            }

            mapData.put(obj);
        }
        return "<div id='map-data' style='display:none;'>" + mapData.toString() + "</div>";
    }

    // 실제로 여행계획에 사용되는 장소명 추출
    private List<String> extractUsedPlaces(String html) {
        List<String> places = new ArrayList<>();
        Pattern pattern = Pattern.compile("<ul id=\"place-list\"[^>]*>(.*?)</ul>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String listContent = matcher.group(1);
            Matcher itemMatcher = Pattern.compile("<li>(.*?)</li>").matcher(listContent);
            while (itemMatcher.find()) {
                places.add(itemMatcher.group(1).trim());
            }
        }
        System.out.println("방문장소 리스트:" + places);
        return places;
    }
}