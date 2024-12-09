package com.EasyTravel.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
}