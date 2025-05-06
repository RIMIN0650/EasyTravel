package com.EasyTravel.postImage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.postImage.domain.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Integer>{
	
	List<PostImage> findByPostId(int postId);
	
}
