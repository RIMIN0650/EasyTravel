package com.EasyTravel.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.recommendation.domain.Recommendation;
import com.EasyTravel.recommendation.repository.RecommendationRepository;

@Service
public class RecommendationService {
	
	@Autowired
	private RecommendationRepository recommendationRepository;
	
	public Recommendation addReccomend(int postId, int userId) {
		
		Recommendation recommendation = Recommendation.builder()
														.postId(postId)
														.userId(userId)
														.build();
		
		return recommendationRepository.save(recommendation);
		
	}
	
	
	
	
}
