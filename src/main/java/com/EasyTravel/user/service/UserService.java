package com.EasyTravel.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.user.domain.User;
import com.EasyTravel.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	
	// 사용자 로그인
	public User addUser(String loginId, String password
						, String userName, String email) {
		
		User user = User.builder()
						.loginId(loginId)
						.password(password)
						.userName(userName)
						.email(email)
						.build();
		
		return userRepository.save(user);
	}
	
	public User findUser(String id, String pw) {
		
		return userRepository.findByLoginIdAndPassword(id, pw);
	}
	
	
	
	
}
