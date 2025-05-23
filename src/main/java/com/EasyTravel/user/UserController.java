package com.EasyTravel.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	
}
