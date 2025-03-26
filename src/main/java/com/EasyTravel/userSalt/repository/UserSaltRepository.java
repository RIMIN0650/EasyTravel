package com.EasyTravel.userSalt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.userSalt.domain.UserSalt;

public interface UserSaltRepository extends JpaRepository<UserSalt, Integer> {
	
	public UserSalt findByUserId(String loginId);
	
}
