package com.EasyTravel.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

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

	@GetMapping("/main/chatbot")
	public String getChatBotPage() {
		return "/chatbot/chatbot";
	}
	
	@GetMapping("/main/chat/result")
	public String returnPlan() {
		return "/travel/travel";
	}
	
	@GetMapping("/main/home/logined")
	public String getMainPageLoginned(HttpSession session, Model model) {
		
		String userName = (String) session.getAttribute("userName");
		model.addAttribute("userName", userName);
		
		return  "/travel/main_logined";
	}
	
	@GetMapping("/")
	public String redirectToMain() {
		return "redirect:/main/home";
	}
	
}
