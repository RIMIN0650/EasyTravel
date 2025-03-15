package com.EasyTravel.recommendation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.repository.PostRepository;
import com.EasyTravel.recommendation.domain.Recommendation;
import com.EasyTravel.recommendation.repository.RecommendationRepository;

@Service
public class RecommendationService {
	
	@Autowired
	private RecommendationRepository recommendationRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	// 추천하기
	public Recommendation addRecommend(int postId, int userId) {
		
		Recommendation recommendation = Recommendation.builder()
														.postId(postId)
														.userId(userId)
														.build();
		
		// Post에 recCount 추가
		// recCount 테이블에도 데이터 추가하고 기존 Post 데이터에도 추천 수 +1 하기
		Optional<Post> optionalPost = postRepository.findById(postId);
		Post post = optionalPost.orElse(null);
		
		if(post!= null) {
			post = post.toBuilder()
					.recCount(post.getRecCount() + 1)
					.build();
			
			post = postRepository.save(post);
		}
		
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
			Optional<Post> optionalPost = postRepository.findById(postId);
			Post post = optionalPost.orElse(null);
			
			if(post != null) {
				post = post.toBuilder()
						.recCount(post.getRecCount() - 1)
						.build();
				
				post = postRepository.save(post);
			}
			
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
