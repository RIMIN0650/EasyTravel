package com.EasyTravel.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.recommendation.domain.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer>{

	
	// 추천수 세기
	public int countByPostId(int postId);
	
	// 추천 여부 확인
	public Recommendation findByPostIdAndUserId(int postId, int userId);
	
}
