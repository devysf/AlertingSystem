package com.yte.springreact.alertingsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yte.springreact.alertingsystem.AlertingSystemApplication;
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
	
    @CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/alerts")
	public List<Alerts> findAll() {
		return alertsService.findAll();
	}
	
    @CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/alerts/{alertsId}")
	public Alerts getAlerts(@PathVariable int alertsId) {
		
		Alerts theAlerts = alertsService.findById(alertsId);
		
		if(theAlerts == null)
			throw new RuntimeException("Alerts id not found - " + alertsId);

		return theAlerts;
	}
	
    @CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/alerts")
	public Alerts addAlerts(@RequestBody Alerts theAlerts) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
				
		theAlerts.setId(0);
		
		alertsService.save(theAlerts);
		
		//Uygulama calısırken yeni bir alert gelirse
		//AlertingSystemApplication.newAlert(theAlerts);

		return theAlerts;
	}
    
    @CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/alerts")
	public Alerts updateAlerts(@RequestBody Alerts theAlerts) {
		alertsService.save(theAlerts);
		
		return theAlerts;
	}
	
    @CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/alerts/{alertsId}")
	public String deleteAlerts(@PathVariable int alertsId) {
		Alerts tempAlerts = alertsService.findById(alertsId);
		
		if(tempAlerts==null)
			throw new RuntimeException("Alerts id not found - " + alertsId);
		
		alertsService.deleteById(alertsId);
		
		return "Alerts deleted with id of " + alertsId; 
		
	}
	
}












