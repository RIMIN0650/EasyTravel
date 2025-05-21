package com.EasyTravel.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetInfoController {
	
	@GetMapping("/find/eatery")
	public String findEatery() {
		return "/food/food";
	}
	
	@GetMapping("/find/hotel")
	public String findHotel() {
		return "/hotel/hotel";
	}
}
