package com.EasyTravel.post.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter

public class PostPreview {
	private int id;
	private int regionId;
	private String title;
	private String userName;
	private Date createdAt;
	private int viewCount;
	private int recCount;
}
