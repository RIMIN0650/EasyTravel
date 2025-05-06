package com.EasyTravel.postImage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.postImage.domain.PostImage;
import com.EasyTravel.postImage.repository.PostImageRepository;

@Service
public class PostImageService {
	
	@Autowired
	private PostImageRepository postImageRepository;
	
	
	// 게시물에 연결된 사진 리스트 불러오기
	public List<PostImage> getImageList(int postId){
		
		return postImageRepository.findByPostId(postId);
		
	}
	
}
