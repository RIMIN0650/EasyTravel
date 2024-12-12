package com.EasyTravel.region.service;

import java.util.List;
import java.util.Optional;

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
	
	
	// regionId로 지역명 찾기
	public Region findRegionName(int regionId) {
		Optional<Region> optionalRegion = regionRepository.findById(regionId);
		Region region = optionalRegion.orElse(null);
		
		if(region != null) {
			return region;
		} else {
			return null;
		}
		
	}
	
	
	
}
