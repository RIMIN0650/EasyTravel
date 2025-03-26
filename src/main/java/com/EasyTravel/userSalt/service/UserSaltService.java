package com.EasyTravel.userSalt.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.common.EncryptUtils;
import com.EasyTravel.userSalt.domain.UserSalt;
import com.EasyTravel.userSalt.repository.UserSaltRepository;

@Service
public class UserSaltService {
	
	@Autowired
	private UserSaltRepository userSaltRepository;
	
	// 사용자 별 salt값 저장
	public UserSalt saveUserSalt(String loginId) throws NoSuchAlgorithmException {
		String saltForPassword = EncryptUtils.createSalt();
		
		UserSalt userSalt = UserSalt.builder()
									.userId(loginId)
									.salt(saltForPassword)
									.build();
		
		return userSaltRepository.save(userSalt);
	}
	
}
