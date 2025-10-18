package com.jmteamconsulting.sgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
}
