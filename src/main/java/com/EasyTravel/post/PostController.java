package com.EasyTravel.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.service.PostService;
import com.EasyTravel.region.domain.Region;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/post/home")
	public String managerJoin(Model model) {
		
		List<Region> regionList = postService.getRegionList();
		
		model.addAttribute("regionList", regionList);
		
		return "post/main";
	}
	
	// 게시물 리스트 보여주기
	@GetMapping("/post/list")
	public String viewPostList(Model model) {
		
		List<PostPreview> postPreview = postService.getPostList();
		
		model.addAttribute("postPreview", postPreview);
		
		
		return "post/list";
	}
	
	
	@GetMapping("/post/upload")
	public String uploadPost() {
		return "post/upload";
	}
}
