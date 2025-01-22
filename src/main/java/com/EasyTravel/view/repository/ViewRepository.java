package com.EasyTravel.view.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.view.domain.View;

public interface ViewRepository extends JpaRepository<View, Integer>{
	
}
