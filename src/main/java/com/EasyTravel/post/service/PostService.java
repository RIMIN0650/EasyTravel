package com.EasyTravel.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.post.domain.Post;
import com.EasyTravel.post.dto.PostPreview;
import com.EasyTravel.post.repository.PostRepository;
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
						.viewCount(0)
						.build();
		
		
		
		return postRepository.save(post);
		
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
			
			// 여기 post.getUserId()가 안됨
			
			int userId = post.getUserId();
			Optional<User> optionalUser = userRepository.findById(post.getUserId());
			User user = optionalUser.orElse(null);
			
			PostPreview postPreview = PostPreview.builder()
											.id(post.getId())
											.regionId(post.getRegionId())
											.userName(user.getUserName())
											.title(post.getTitle())
											.viewCount(post.getViewCount())
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
	
	
	
	
}
