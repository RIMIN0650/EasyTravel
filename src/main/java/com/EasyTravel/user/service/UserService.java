package com.EasyTravel.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.common.EncryptUtils;
import com.EasyTravel.user.domain.User;
import com.EasyTravel.user.repository.UserRepository;
import com.EasyTravel.userSalt.domain.UserSalt;
import com.EasyTravel.userSalt.repository.UserSaltRepository;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public UserSaltRepository userSaltRepository;
	
	// 사용자 회원가입
	public User addUser(String loginId, String password
						, String userName, String email) {
		
		UserSalt userSalt = userSaltRepository.findByUserId(loginId);
		
		String encryptedPassword = EncryptUtils.encrypt(password, userSalt.getSalt());
		
		User user = User.builder()
						.loginId(loginId)
						.password(encryptedPassword)
						.userName(userName)
						.email(email)
						.build();
		
		return userRepository.save(user);
	}
	
	
	// 로그인 기능
	public User findUser(String id, String pw) {
		
		UserSalt userSalt = userSaltRepository.findByUserId(id);
		
		String encryptedPassword = EncryptUtils.encrypt(id, userSalt.getSalt());
		
		return userRepository.findByLoginIdAndPassword(id, encryptedPassword);
	}
	
	
	
	
}
