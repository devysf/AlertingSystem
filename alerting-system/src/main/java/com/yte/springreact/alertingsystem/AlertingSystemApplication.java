package com.yte.springreact.alertingsystem;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;


@SpringBootApplication
public class AlertingSystemApplication {
	
	private static AlertsService alertsService;
	
	@Autowired
	public AlertingSystemApplication(AlertsService theAlertsService) {
		alertsService = theAlertsService;
	}
	
	public static void main(String[] args) throws Exception{
		
		SpringApplication.run(AlertingSystemApplication.class, args);		
		
		System.out.println("---------------------in main -------------------------------------------");
		System.out.println("---------------------in main -------------------------------------------");
		System.out.println("---------------------in main -------------------------------------------");

		System.out.println(alertsService.findAll());
		
		List<Alerts> listOfAlerts = alertsService.findAll();
		
		for(Alerts alerts : listOfAlerts) {
			MyThread th = new MyThread(alertsService,alerts);
			
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();	    
			
			service.scheduleAtFixedRate(th, 0, alerts.getPeriod(), TimeUnit.MILLISECONDS);
			
		}
	
	}
	
	 public static void newAlert(Alerts alerts) {
		 MyThread th = new MyThread(alertsService,alerts);
			
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();	    
		
		service.scheduleAtFixedRate(th, 0, alerts.getPeriod(), TimeUnit.MILLISECONDS);
    }
	
	

}
