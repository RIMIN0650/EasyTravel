package com.EasyTravel.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.user.domain.User;
import com.EasyTravel.user.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	public UserService userService;
	
	// 사용자 회원가입
	@PostMapping("/user/join")
	public Map<String, String> userJoin(@RequestParam("loginId") String loginId
										, @RequestParam("password") String password
										, @RequestParam("userName") String userName
										, @RequestParam("email") String email
										){
		
		User user = userService.addUser(loginId, password, userName, email);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result",  "success");
		} else {
			resultMap.put("result",  "fail");
		}
		
		return resultMap;
		
	}
	
	
}
