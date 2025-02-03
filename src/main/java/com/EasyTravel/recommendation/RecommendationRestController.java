package com.EasyTravel.recommendation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	
	// 추천 누르기
	@PostMapping("/post/recommend")
	public Map<String, String> reccomend(@RequestParam("postId") int postId, HttpSession session){
		int userId = (Integer)session.getAttribute("userId");
		
		Recommendation recommendation = recommendationService.addRecommend(postId, userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(recommendation != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
	// 추천 취소하기
	@DeleteMapping("/post/deleteRecommend")
	public Map<String, String> deleteRecommend(@RequestParam("postId") int postId, HttpSession session){
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(recommendationService.deleteRecommend(postId, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	
	
	
	
	
}
