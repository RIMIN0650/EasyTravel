package com.EasyTravel.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PopularPost {
	private int id;
	private String regionName;
	private String title;
	private String userName;
	private int recCount;
}
