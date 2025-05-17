package com.EasyTravel.preference.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.preference.domain.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, Integer>{
	
}
