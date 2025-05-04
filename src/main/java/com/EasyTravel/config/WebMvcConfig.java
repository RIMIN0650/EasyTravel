package com.EasyTravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.EasyTravel.common.FileManager;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 이미지를 특정 URL 패턴과 파일 시스템의 경로에 매핑
	
	@Override // addResourceHandlers : WebMvcConfigurer 인터페이스의 메서드로 
				//정적 자원의 핸들러를 추가하기 위해 오버라이드
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH + "/");
		//.addResourceLocations("file://" + FileManager.FILE_UPLOAD_PATH + "/");
	}
	// 웹 어플리케이션에서 업로드된 이미지 파일을 URL을 통해 접근할 수 있도록 함
}