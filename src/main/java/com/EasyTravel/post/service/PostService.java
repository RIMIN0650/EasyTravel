package com.EasyTravel.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTravel.region.domain.Region;
import com.EasyTravel.region.repository.RegionRepository;

@Service
public class PostService {
	@Autowired
	private RegionRepository regionRepository;
	
	
	
	public List<Region> getRegionList(){
		List<Region> regionList = regionRepository.findAll();
		List<Region> newRegionList = new ArrayList<>();
		for(Region region : regionList) {
			Region regions = Region.builder()
									.id(region.getId())
									.name(region.getName())
									.build();
			newRegionList.add(regions);
			
		}
		return newRegionList;
	}
}
