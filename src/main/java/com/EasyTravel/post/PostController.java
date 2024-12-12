package com.EasyTravel.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.service.PostService;
import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.service.RegionService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private RegionService regionService;
	
	@GetMapping("/post/home")
	public String managerJoin(Model model) {
		
		List<Region> regionList = postService.getRegionList();
		
		model.addAttribute("regionList", regionList);
		
		return "post/main";
	}
	
	// 게시물 리스트 보여주기
	@GetMapping("/post/list")
	public String viewPostList(@RequestParam("regionId") int regionId, Model model) {
		
		
		
		List<PostPreview> postPreview = postService.getPostList(regionId);
		
		Region region = regionService.findRegionName(regionId);
		
		
		model.addAttribute("postPreview", postPreview);
		model.addAttribute("regionName", region.getName());
		
		return "post/list";
	}
	
	
	// 게시물 업로드 페이지
	@GetMapping("/post/upload")
	public String uploadPost(Model model) {
		
		List<Region> regionList = regionService.getRegionList();
		
		model.addAttribute("regionList", regionList);
		
		return "post/upload";
	}
}
