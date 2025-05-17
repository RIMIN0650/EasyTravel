package com.EasyTravel.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.dto.PopularPost;
import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.service.PostService;
import com.EasyTravel.postImage.domain.PostImage;
import com.EasyTravel.postImage.service.PostImageService;
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
	
	@Autowired
	private PostImageService postImageService;
	
	
	// 메인 페이지
	@GetMapping("/post/home")
	public String managerJoin(Model model) {
		
		// 지역 리스트 불러오기
		List<Region> regionList = postService.getRegionList();
		
		// 추천 수 높은 상위 4개 게시물 불러오기
		List<PopularPost> popularPostList = postService.getTopRecPost();
		
		
		model.addAttribute("regionList", regionList);
		model.addAttribute("postList", popularPostList);
		
		return "post/main";
	}
	
	
	// 게시물 리스트 보여주기
	@GetMapping("/post/list")
	public String viewPostList(@RequestParam("regionId") int regionId, Model model) {
		
		List<PostPreview> postPreview = postService.getPostList(regionId);
		
		List<PostPreview> viewOrder = postService.getPostListOrderByViewCount(regionId);
		
		List<PostPreview> recOrder = postService.getPostListOrderByRecCount(regionId);
		
		Region region = regionService.findRegionName(regionId);
		
		
		
		model.addAttribute("postPreview", postPreview);
		model.addAttribute("viewOrder", viewOrder);
		model.addAttribute("recOrder", recOrder);
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
		
		List<PostImage> imageList = postImageService.getImageList(postId);
		
		int recCount = recommendationService.countRecommend(postId);
		
		// 게시물 눌렀을 때 해당 id의 게시물 조회수 + 1 해주기
		postService.addPostViewCount(postId);
		
		// 사용자가 특정 게시물 추천 여부 확인
		boolean checkRecommend = recommendationService.checkRecommend(postId, recCount);
		
		
		model.addAttribute("post", post);
		model.addAttribute("imageList", imageList);
		model.addAttribute("recCount", recCount);
		model.addAttribute("checkRecommend",checkRecommend);
		
		return "post/detail";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
