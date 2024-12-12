package com.EasyTravel.region.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.repository.RegionRepository;

@Service
public class RegionService {
	@Autowired
	private RegionRepository regionRepository;
	
	public List<Region> getRegionList(){
		
		List<Region> regionList = regionRepository.findAll();
		
		return regionList;
	}
	
	
}
