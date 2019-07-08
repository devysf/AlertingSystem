package com.yte.springreact.alertingsystem.service;

import java.util.List;

import com.yte.springreact.alertingsystem.entity.Alerts;

public interface AlertsService {
	
	public List<Alerts> findAll();

	public Alerts findById(int alertsId);

	public Alerts save(Alerts theAlerts);

	public void deleteById(int alertsId);

		
}

