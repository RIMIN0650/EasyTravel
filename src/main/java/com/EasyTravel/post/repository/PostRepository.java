package com.EasyTravel.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	// 지역 id와 일치하는 게시물 모두 조회
	List<Post> findAllByRegionId(int regionId);
	
	
}
