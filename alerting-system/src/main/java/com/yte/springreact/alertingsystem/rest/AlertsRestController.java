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

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
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
	
	@GetMapping("/alerts")
	public List<Alerts> findAll() {
		return alertsService.findAll();
	}
	
	@GetMapping("/alerts/{alertsId}")
	public Alerts getAlerts(@PathVariable int alertsId) {
		
		Alerts theAlerts = alertsService.findById(alertsId);
		
		if(theAlerts == null)
			throw new RuntimeException("Alerts id not found - " + alertsId);

		return theAlerts;
	}

	@GetMapping("/alertsByUsername/{username}")
	public Set<Alerts> getAlertsByUsername(@PathVariable String username) {

		User foundUser = userService.findByUsername(username);

		if(foundUser == null)
			throw new RuntimeException("User not found - " + username);

		return foundUser.getAlerts();
	}


	
	@PostMapping("/alerts")
	public ResponseEntity<?> addAlerts(HttpServletRequest request, @RequestBody Alerts theAlerts) {

		User foundUser = userService.findByUsername(request.getUserPrincipal().getName());

		theAlerts.setCreatedBy(foundUser.getUsername());

		try{
			//theAlerts are saved into db because of getting id from db. Then push into found users set of alerts.
			Alerts savedAlerts = alertsService.save(theAlerts);
			foundUser.getAlerts().add(savedAlerts);
		}
		catch (ConstraintViolationException e){
			//Here, we control if there are unvalid input. İf there are, we return warning message.
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
    
	@PutMapping("/alerts")
	public ResponseEntity<?> updateAlerts(HttpServletRequest request, @RequestBody Alerts theAlerts) {
		Alerts foundAlerts = alertsService.findById(theAlerts.getId());

		String createdBy = foundAlerts.getCreatedBy();
		String foundUser = request.getUserPrincipal().getName();

		//Compare users for authorization purposes.
		if(foundUser.equals(createdBy))
		{
			foundAlerts.setName(theAlerts.getName());
			foundAlerts.setHttp_method(theAlerts.getHttp_method());
			foundAlerts.setPeriod(theAlerts.getPeriod());
			foundAlerts.setUrl(theAlerts.getUrl());

			try{
				//There is a bug in here. We can not control invalid input
				alertsService.save(foundAlerts);
				//alertsRepository.save(foundAlerts);
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
				//Now we return general message, not input field specific.
				Map<String ,String> violations = new HashMap<>();
				violations.put("name", "Something goes wrong.Please control every field.");
				return ResponseEntity.ok(violations);

			}


			return ResponseEntity.ok("success");
		}
		else {
			return ResponseEntity.ok(new RuntimeException(foundUser + ", you are update  " +createdBy+ "'s alerts. You dont have permission" ));
		}

	}
	
	@DeleteMapping("/alerts/{alertsId}")
	public ResponseEntity<?> deleteAlerts(HttpServletRequest request,@PathVariable int alertsId) {
		Alerts foundAlerts = alertsService.findById(alertsId);
		User foundUser = userService.findByUsername(request.getUserPrincipal().getName());

		if(foundAlerts==null)
			throw new RuntimeException("Alerts id not found - " + alertsId);

		String createdBy = foundAlerts.getCreatedBy();

		//Compare users for authorization purposes.
		if(foundUser.getUsername().equals(createdBy))
		{
			foundUser.getAlerts().remove(foundAlerts);
			userService.save(foundUser);
			alertsService.deleteById(alertsId);
			return ResponseEntity.ok("success");
		}

		return ResponseEntity.ok( new RuntimeException(foundUser + ", you are delete  " +createdBy+ "'s alerts. You dont have permission" ));
	}
}












