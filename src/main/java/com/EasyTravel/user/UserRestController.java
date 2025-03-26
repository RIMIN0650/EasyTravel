package com.EasyTravel.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.user.domain.User;
import com.EasyTravel.user.service.UserService;
import com.EasyTravel.userSalt.domain.UserSalt;
import com.EasyTravel.userSalt.service.UserSaltService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserRestController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private UserSaltService userSaltService;
	
	
	// 사용자 회원가입 
	@PostMapping("/user/join")
	public Map<String, String> userJoin(@RequestParam("loginId") String loginId
										, @RequestParam("password") String password
										, @RequestParam("userName") String userName
										, @RequestParam("email") String email
										) throws NoSuchAlgorithmException{
		
		UserSalt userSalt = userSaltService.saveUserSalt(loginId);
		
		User user = userService.addUser(loginId, password, userName, email);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result",  "success");
		} else {
			resultMap.put("result",  "fail");
		}
		
		return resultMap;
		
	}
	
	
	
	// 사용자 로그인
	@PostMapping("/user/login")
	public Map<String, String> userLogin(@RequestParam("id") String id, @RequestParam("pw") String pw
										, HttpServletRequest request){
		
		User user = userService.findUser(id, pw);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			
			HttpSession session = request.getSession();
			
			session.setAttribute("userId",  user.getId());
			session.setAttribute("userName",  user.getUserName());
			
			resultMap.put("result",  "success");
		} else {
			resultMap.put("result",  "fail");
		}
		
		return resultMap;
		
	}
	
}
