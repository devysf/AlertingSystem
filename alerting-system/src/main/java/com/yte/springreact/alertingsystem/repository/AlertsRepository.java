package com.yte.springreact.alertingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yte.springreact.alertingsystem.entity.Alerts;


public interface AlertsRepository extends JpaRepository<Alerts, Integer> {

	// that's it ... no need to write any code LOL!
	
}
