package com.EasyTravel.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	
	@RequestMapping("/user/join")
	public String userJoin() {
		
		return "/user/join";
	}
	
	
	
}