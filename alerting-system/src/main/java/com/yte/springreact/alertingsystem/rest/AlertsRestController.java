package com.yte.springreact.alertingsystem.rest;

import java.util.List;

import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yte.springreact.alertingsystem.AlertingSystemApplication;
import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AlertsRestController {
			
	private AlertsService alertsService;
	private UserService userService;

	@Autowired
	public AlertsRestController(AlertsService theAlertsService,UserService theUserService) {
		alertsService = theAlertsService;
		userService = theUserService;
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
	public User addAlerts(HttpServletRequest request, @RequestBody Alerts theAlerts) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
				
		theAlerts.setId(0);
		System.out.println("Adding alerts");
		System.out.println(request.getUserPrincipal().getName());
		User foundUser = userService.findByUsername(request.getUserPrincipal().getName());
		System.out.println("Id --> " + foundUser.getId());

		theAlerts.setCreatedBy(foundUser.getUsername());
		Alerts savedAlerts = alertsService.save(theAlerts);
		foundUser.getAlerts().add(savedAlerts);


		userService.save(foundUser);

		return foundUser;
	}
    
    @CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/alerts")
	public Alerts updateAlerts(@RequestBody Alerts theAlerts) {
		System.out.println("Alerts asdaaaaaaaaaaaaaaaaa" + theAlerts);

		Alerts foundAlerts = alertsService.findById(theAlerts.getId());
		foundAlerts.setName(theAlerts.getName());
		foundAlerts.setHttp_method(theAlerts.getHttp_method());
		foundAlerts.setPeriod(theAlerts.getPeriod());
		foundAlerts.setUrl(theAlerts.getUrl());

		alertsService.save(foundAlerts);
		
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












