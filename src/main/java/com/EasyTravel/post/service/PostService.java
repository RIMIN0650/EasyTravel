package com.EasyTravel.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.repository.PostRepository;
import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.repository.RegionRepository;

@Service
public class PostService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
	// 새로운 지역 등록
	public List<Region> getRegionList(){
		List<Region> regionList = regionRepository.findAll();
		List<Region> newRegionList = new ArrayList<>();
		for(Region region : regionList) {
			Region regions = Region.builder()
									.id(region.getId())
									.name(region.getName())
									.build();
			newRegionList.add(regions);
			
		}
		return newRegionList;
	}
	
	
	// 새로운 게시물 등록
	public Post addPost(int regionId, int userId, String title, String body) {
		
		Post post = Post.builder()
						.regionId(regionId)
						.userId(userId)
						.title(title)
						.body(body)
						.build();
		
		return postRepository.save(post);
		
	}
	
	
	
	
	
}
