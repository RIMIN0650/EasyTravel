package com.EasyTravel.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.EasyTravel.common.FileManager;
import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.dto.PopularPost;
import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.repository.PostRepository;
import com.EasyTravel.postImage.domain.PostImage;
import com.EasyTravel.postImage.repository.PostImageRepository;
import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.repository.RegionRepository;
import com.EasyTravel.user.domain.User;
import com.EasyTravel.user.repository.UserRepository;

@Service
public class PostService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostImageRepository postImageRepository;
	
//	@Autowired
//	private RecommendationRepository recommendationRepository;
	
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
	public Post addPost(int regionId, int userId, String title
						, String body , List<MultipartFile> imageFiles) { 
		
		
		
		Post post = Post.builder()
						.regionId(regionId)
						.userId(userId)
						.title(title)
						.body(body)
						.viewCount(0)
						.recCount(0)
						.build();
		
		postRepository.save(post);
		
		for(MultipartFile file : imageFiles) {
			if(!file.isEmpty()) {
				String filePath = FileManager.saveFile(title, file);
				PostImage image = new PostImage();
				image.setPost(post);
				image.setImagePath(filePath);
				postImageRepository.save(image);
				
			}
		}
		
		return post;
		
		
		
		
		
		// 게시물 조회수를 따로 테이블 만들려고 했는데
		// regionId, userId, title, body만을 가지고 중복을 구분할 수 없을 것 같아서
		// 내용까지 완벽히 똑같을 확률은 매우 드물겠지만
		// 그래도 완벽하게 하기 위해 조회수를 기존 post 테이블의 컬럼으로 추가
			
	}
	
	
	// 게시물 전체 리스트 가져오기
	public List<PostPreview> getPostList(int regionId){
		
		List<Post> postList = postRepository.findAllByRegionId(regionId);
		
		List<PostPreview> postPreviewList = new ArrayList<>();
		
		for(Post post:postList) {
			
			Optional<User> optionalUser = userRepository.findById(post.getUserId());
			User user = optionalUser.orElse(null);
			
//			int recCount = recommendationRepository.countByPostId(post.getId());
			
			PostPreview postPreview = PostPreview.builder()
											.id(post.getId())
											.regionId(post.getRegionId())
											.userName(user.getUserName())
											.title(post.getTitle())
											.viewCount(post.getViewCount())
											.recCount(post.getRecCount())
											.build();
			
			postPreviewList.add(postPreview);
		}
		
		return postPreviewList;
	}
	
	
	// 추천수 많은 순서대로 게시글 불러오기
	public List<PostPreview> getPostListOrderByViewCount(int regionId){
		List<Post> postList = postRepository.findTop5ByRegionIdOrderByViewCountDesc(regionId);
		
		List<PostPreview> postPreviewList = new ArrayList<>();
		
		for(Post post:postList) {
			Optional<User> optionalUser = userRepository.findById(post.getUserId());
			User user = optionalUser.orElse(null);
			
			PostPreview postPreview = PostPreview.builder()
												.id(post.getId())
												.regionId(post.getRegionId())
												.userName(user.getUserName())
												.title(post.getTitle())
												.viewCount(post.getViewCount())
												.recCount(post.getRecCount())
												.build();
			
			postPreviewList.add(postPreview);
			
		}
		return postPreviewList;
	}
	
	
	// 추천 순 내림차순 조회
	public List<PostPreview> getPostListOrderByRecCount(int regionId){
		List<Post> postList = postRepository.findTop5ByRegionIdOrderByRecCountDesc(regionId);
		
		List<PostPreview> postPreviewList = new ArrayList<>();
		
		for(Post post : postList) {
			Optional<User> optionalUser = userRepository.findById(post.getUserId());
			User user = optionalUser.orElse(null);
			
			PostPreview postPreview = PostPreview.builder()
												.id(post.getId())
												.regionId(post.getRegionId())
												.userName(user.getUserName())
												.title(post.getTitle())
												.viewCount(post.getViewCount())
												.recCount(post.getRecCount())
												.build();
			postPreviewList.add(postPreview);
			
		}
		return postPreviewList;
	}
	
	// 게시물 내용 불러오기
	public Post getPostDetail(int postId) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		
		Post post = optionalPost.orElse(null);
		
		return post;
	}
	
	
	// 게시물 조회수 1 증가시키기
	public Post addPostViewCount(int postId) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		Post post = optionalPost.orElse(null);
		
		if(post != null) {
			post = post.toBuilder()
						.viewCount(post.getViewCount() + 1)
						.build();
			post = postRepository.save(post);
		}
		
		return post;
	}
	
	
	// 추천 순 많은 게시물 상위 4개 불러오기
	public List<PopularPost> getTopRecPost() {
		List<Post> topPostList = postRepository.findTop5ByOrderByRecCountDesc();
		
		List<PopularPost> popularPostList = new ArrayList<>();
		
		
		
		for(Post post : topPostList) {
			Optional<Region> optionalRegion = regionRepository.findById(post.getRegionId());
			Region region = optionalRegion.orElse(null);
			
			Optional<User> optionalUser = userRepository.findById(post.getUserId());
			User user = optionalUser.orElse(null);
			
			PopularPost popularPost = PopularPost.builder()
													.regionName(region.getName())
													.title(post.getTitle())
													.userName(user.getUserName())
													.recCount(post.getRecCount())
													.build();
			popularPostList.add(popularPost);
		}
		
		
		
		return popularPostList;
	}
	
	
	
}
