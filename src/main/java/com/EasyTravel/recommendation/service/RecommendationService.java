package com.EasyTravel.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.recommendation.domain.Recommendation;
import com.EasyTravel.recommendation.repository.RecommendationRepository;

@Service
public class RecommendationService {
	
	@Autowired
	private RecommendationRepository recommendationRepository;
	
	// 추천하기
	public Recommendation addRecommend(int postId, int userId) {
		
		Recommendation recommendation = Recommendation.builder()
														.postId(postId)
														.userId(userId)
														.build();
		
		return recommendationRepository.save(recommendation);
		
	}
	
	// 추천수 조회하기
	public int countRecommend(int postId) {
		
		return recommendationRepository.countByPostId(postId);
	}
	
	
	
	// 추천 삭제하기
	public boolean deleteRecommend(int postId, int userId) {
		
		Recommendation recommendation = recommendationRepository.findByPostIdAndUserId(postId, userId);
		
		if(recommendation != null) {
			recommendationRepository.delete(recommendation);
			return true;
		} else {
			return false;
		}
	}
	
	
	// 사용자별 게시물 추천 여부 확인
	public boolean checkRecommend(int postId, int userId) {
		
		Recommendation recommendation = recommendationRepository.findByPostIdAndUserId(postId, userId);
		
		if(recommendation != null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
