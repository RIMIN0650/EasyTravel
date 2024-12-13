package com.EasyTravel.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByLoginIdAndPassword(String loginId, String Password);
	
}
