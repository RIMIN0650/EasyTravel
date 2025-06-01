package com.EasyTravel.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/main/home")
	public String getMainPage() {
		return "/travel/main";
	}
	
	@GetMapping("/main/chat")
	public String createPlan() {
		return "/travel/form";
	}
	
	@GetMapping("/main/chat/result")
	public String returnPlan() {
		return "/travel/travel";
	}
}
