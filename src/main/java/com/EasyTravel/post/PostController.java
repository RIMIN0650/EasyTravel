package com.EasyTravel.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.service.PostService;
import com.EasyTravel.recommendation.service.RecommendationService;
import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.service.RegionService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RecommendationService recommendationService;
	
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
	
	// 게시물 확인 페이지
	// 게시물 추천수 조회하기
	@GetMapping("/post/view")
	public String viewPost(@RequestParam("postId") int postId, Model model) {
		
		Post post = postService.getPostDetail(postId);
		
		int recCount = recommendationService.countRecommend(postId);
		
		// 게시물 눌렀을 때 해당 id의 게시물 조회수 + 1 해주기
		postService.addPostViewCount(postId);
		
		// 사용자가 특정 게시물 추천 여부 확인
		boolean checkRecommend = recommendationService.checkRecommend(postId, recCount);
	
		model.addAttribute("post", post);
		model.addAttribute("recCount", recCount);
		model.addAttribute("checkRecommend",checkRecommend);
		
		return "post/detail";
	}
	
	
}
