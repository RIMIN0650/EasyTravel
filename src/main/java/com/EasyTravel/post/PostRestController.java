package com.EasyTravel.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.service.PostService;

@RestController
public class PostRestController {
	
	@Autowired
	public PostService postService;
	
	// 지역 게시판 게시물 등록
	@PostMapping("/post/upload")
	public Map<String, String> postUpload(@RequestParam("regionId") int regionId
											, @RequestParam("userId") int userId
											, @RequestParam("title") String title
											, @RequestParam("body") String body){
		
		Post post = postService.addPost(regionId, userId, title, body);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(post != null) {
			resultMap.put("result",  "success");
		} else {
			resultMap.put("result", "fail");
		}	
		
		return resultMap;
	}
	
	
	
	
	
	
}
