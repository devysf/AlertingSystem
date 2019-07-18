package com.yte.springreact.alertingsystem.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yte.springreact.alertingsystem.AlertingSystemApplication;
import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api")
public class AlertsRestController {
			
	private AlertsService alertsService;
	private UserService userService;
	private AlertsRepository alertsRepository;

	@Autowired
	public AlertsRestController(AlertsService theAlertsService,UserService theUserService, AlertsRepository theAlertsRepository) {
		alertsService = theAlertsService;
		userService = theUserService;
		alertsRepository = theAlertsRepository;
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
	@GetMapping("/alertsByUsername/{username}")
	public Set<Alerts> getAlertsByUsername(@PathVariable String username) {

		User foundUser = userService.findByUsername(username);

		if(foundUser == null)
			throw new RuntimeException("User not found - " + username);

		return foundUser.getAlerts();
	}


	
    @CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/alerts")
	public ResponseEntity<?> addAlerts(HttpServletRequest request, @RequestBody Alerts theAlerts) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
				
		theAlerts.setId(0);
		System.out.println("Adding alerts");
		System.out.println(request.getUserPrincipal().getName());
		User foundUser = userService.findByUsername(request.getUserPrincipal().getName());
		System.out.println("Id --> " + foundUser.getId());

		theAlerts.setCreatedBy(foundUser.getUsername());
		try{
			Alerts savedAlerts = alertsService.save(theAlerts);
			foundUser.getAlerts().add(savedAlerts);
		}
		catch (ConstraintViolationException e){
			System.out.println("Alerts save is fail. " + e.getConstraintViolations());

			Map<String,String> violations = new HashMap() ;
			for(ConstraintViolation cv : e.getConstraintViolations()){
				System.out.println(cv);
				violations.put(cv.getPropertyPath().toString(),cv.getMessage());
			}

			return ResponseEntity.ok(violations);
		}


		userService.save(foundUser);

		return ResponseEntity.ok("success");
	}
    
    @CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/alerts")
	public ResponseEntity<?> updateAlerts(HttpServletRequest request, @RequestBody Alerts theAlerts) {
		Alerts foundAlerts = alertsService.findById(theAlerts.getId());

		System.out.println("Alerts asdaaaaaaaaaaaaaaaaa" + theAlerts);

		String foundUser = request.getUserPrincipal().getName();
		String createdBy = foundAlerts.getCreatedBy();
		System.out.println("USERRRR --> " + foundUser + "   "  + createdBy);

		if(foundUser.equals(createdBy))
		{
			foundAlerts.setName(theAlerts.getName());
			foundAlerts.setHttp_method(theAlerts.getHttp_method());
			foundAlerts.setPeriod(theAlerts.getPeriod());
			foundAlerts.setUrl(theAlerts.getUrl());


			try{
				//alertsService.save(foundAlerts);
				alertsRepository.save(foundAlerts);
			}
			catch (ConstraintViolationException e){
				System.out.println("Alerts update is fail. " + e.getConstraintViolations());

				Map<String,String> violations = new HashMap() ;
				for(ConstraintViolation cv : e.getConstraintViolations()){
					System.out.println(cv);
					violations.put(cv.getPropertyPath().toString(),cv.getMessage());
				}

				return ResponseEntity.ok(violations);
			}
			catch (Exception e){
				return ResponseEntity.ok("failed in e" +e );

			}


			return ResponseEntity.ok("success");
		}
		else {
			return ResponseEntity.ok(new RuntimeException(foundUser + ", you are update  " +createdBy+ "'s alerts. You dont have permission" ));
		}

	}
	
    @CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/alerts/{alertsId}")
	public String deleteAlerts(HttpServletRequest request,@PathVariable int alertsId) {
		Alerts foundAlerts = alertsService.findById(alertsId);
		
		if(foundAlerts==null)
			throw new RuntimeException("Alerts id not found - " + alertsId);


		String foundUser = request.getUserPrincipal().getName();
		String createdBy = foundAlerts.getCreatedBy();
		System.out.println("USERRRR --> " + foundUser + "   "  + createdBy);

		if(foundUser.equals(createdBy))
		{
			alertsService.deleteById(alertsId);
			return "Success";
		}



		throw new RuntimeException(foundUser + ", you are delete  " +createdBy+ "'s alerts. You dont have permission" );

	}
	
}












