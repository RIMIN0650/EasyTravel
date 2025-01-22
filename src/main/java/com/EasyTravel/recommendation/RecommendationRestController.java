package com.EasyTravel.recommendation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.recommendation.domain.Recommendation;
import com.EasyTravel.recommendation.service.RecommendationService;

import jakarta.servlet.http.HttpSession;

@RestController
public class RecommendationRestController {
	
	@Autowired
	private RecommendationService recommendationService;
	
	@PostMapping("/post/recommend")
	public Map<String, String> reccomend(@RequestParam("postId") int postId, HttpSession session){
		int userId = (Integer)session.getAttribute("userId");
		
		Recommendation recommendation = recommendationService.addReccomend(postId, userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(recommendation != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
	
	
	
}
