package com.yte.springreact.alertingsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;

@RestController
@RequestMapping("/api")
public class AlertsRestController {
			
	private AlertsService alertsService;
	
	@Autowired
	public AlertsRestController(AlertsService theAlertsService) {
		alertsService = theAlertsService;
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World! ";
	}
	
	@GetMapping("/alerts")
	public List<Alerts> findAll() {
		return alertsService.findAll();
	}
	
	
}












