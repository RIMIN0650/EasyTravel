package com.EasyTravel.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.user.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	public UserService userService;
	
	
	
	
}
