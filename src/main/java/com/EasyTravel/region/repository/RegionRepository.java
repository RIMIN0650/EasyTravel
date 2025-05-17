package com.EasyTravel.region.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyTravel.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Integer>{
	public Optional<Region> findById(int id);
}
