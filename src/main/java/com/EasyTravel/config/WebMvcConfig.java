package com.EasyTravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.EasyTravel.common.FileManager;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 기본 정적 리소스 핸들러들 추가

		// 1. CSS 파일들
		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/");

		// 2. JavaScript 파일들
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");

		// 3. 이미지 파일들 (static 폴더 내)
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");

		// 4. 기타 정적 파일들
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");

		// 5. 업로드된 파일들 (외부 경로)
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH + "/");

		// 6. 메인 폴더의 정적 파일들 (특별히 main 폴더가 있다면)
		registry.addResourceHandler("/main/**")
				.addResourceLocations("classpath:/static/main/");

		// 7. 루트 레벨 정적 파일들
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/")
				.addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/META-INF/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 뷰 컨트롤러 추가 (필요시)
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.addViewController("/chatbot").setViewName("chatbot");
	}
}