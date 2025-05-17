package com.EasyTravel.preference.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.preference.domain.Preference;
import com.EasyTravel.preference.repository.PreferenceRepository;

@Service
public class PreferenceService {
	
	@Autowired
	public PreferenceRepository preferenceRepository;
	
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
	
	
	
}
