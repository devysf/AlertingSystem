package com.yte.springreact.alertingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;

@Service
public class AlertsServiceImpl implements AlertsService{
	
	
	private AlertsRepository alertsRepository;
	
	@Autowired
	public AlertsServiceImpl(AlertsRepository theAlertsRepository) {
		alertsRepository = theAlertsRepository;
	}


	@Override
	public List<Alerts> findAll() {
		return alertsRepository.findAll();
	}
	
	
}
