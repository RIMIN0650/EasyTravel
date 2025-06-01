package com.EasyTravel.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetInfoController {
	
	@GetMapping("/main/food")
	public String findEatery() {
		return "/food/food";
	}
	
	@GetMapping("/main/hotel")
	public String findHotel() {
		return "/hotel/hotel";
	}
}
