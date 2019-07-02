package com.yte.springreact.alertingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yte.springreact.alertingsystem.entity.Results;


public interface ResultsRepository extends JpaRepository<Results, Integer> {

	// that's it ... no need to write any code LOL!
	
}
