package com.yte.springreact.alertingsystem.service;

import java.util.List;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;

public interface ResultsService {
	
	public List<Results> findAll();

	public Results findById(int alertsId);

	public void save(Results theAlerts);

	public void deleteById(int alertsId);

		
}

