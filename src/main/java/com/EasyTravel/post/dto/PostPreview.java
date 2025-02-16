package com.EasyTravel.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter

public class PostPreview {
	private int id;
	private int regionId;
	private String userName;
	private String title;
	private int recCount;
	private int viewCount;
}
