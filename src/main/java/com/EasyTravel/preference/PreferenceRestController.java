package com.EasyTravel.preference;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTravel.preference.domain.Preference;
import com.EasyTravel.preference.service.PreferenceService;

import jakarta.servlet.http.HttpSession;

@RestController
public class PreferenceRestController {
	
	@Autowired
	public PreferenceService preferenceService;
	
	@PostMapping("/user/add/preference")
	public Map<String, String> addUserPreference(@RequestParam("first") String first
													, @RequestParam("second") String second
													, @RequestParam("third") String third
													, @RequestParam("etcetra") String etcetra
													, HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Preference preference = preferenceService.savePreference(userId, first, second, third, etcetra);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(preference != null) {
			resultMap.put("result",  "success");
		} else {
			resultMap.put("result",  "fail");
		}
		
		return resultMap;
	}
	
	
	
	
}
