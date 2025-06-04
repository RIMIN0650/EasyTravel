package com.EasyTravel.preference.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.preference.domain.Preference;
import com.EasyTravel.preference.repository.PreferenceRepository;

@Service
public class PreferenceService {
	
	@Autowired
	public PreferenceRepository preferenceRepository;
	
	// 사용자 취향 저장하기
	public Preference savePreference(int userId, String first, String second
									, String third, String etcetra) {
		
		Preference preference = Preference.builder()
											.userId(userId)
											.first(first)
											.second(second)
											.third(third)
											.etcetra(etcetra)
											.build();
		
		return preferenceRepository.save(preference);
		
	}
	
	// 사용자 취향 불러오기
	public Preference findPreference(int id) {
		
		Optional<Preference> optionalPreference = preferenceRepository.findById(id);
		Preference preference = optionalPreference.orElse(null);
		
		
		return preference;
	}
	
	
	
}
