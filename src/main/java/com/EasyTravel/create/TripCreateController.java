package com.EasyTravel.create;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TripCreateController {
	@GetMapping("/plan/create")
	public String planCreate() {
		return "/chat/form";
	}
	
	@GetMapping("/chat/travel")
    public String travelPage() {
        return "/chat/travel"; // templates/chat/travel.html
    }
}
