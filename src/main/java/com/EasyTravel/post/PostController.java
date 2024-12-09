package com.EasyTravel.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {
	
	@GetMapping("/post/home")
	public String managerJoin() {
		return "post/main";
	}
	
	
}
