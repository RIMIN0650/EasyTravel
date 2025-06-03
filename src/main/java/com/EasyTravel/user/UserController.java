package com.EasyTravel.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	
	@RequestMapping("/user/join")
	public String userJoin() {
		
		return "/user/signup";
	}
	
	@RequestMapping("/user/login")
	public String userLogin() {
		
		return "/user/login";
	}
	
	@GetMapping("/user/preference/store")
	public String userPreference() {
		return "/user/userPrefer";
	}
	
	// 로그아웃 기능
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		return  "redirect:/main/home";
	}
	
}
