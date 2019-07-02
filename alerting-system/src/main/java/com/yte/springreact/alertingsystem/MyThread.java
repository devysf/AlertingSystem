package com.yte.springreact.alertingsystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;

class MyThread implements Runnable {
	Alerts alerts;
	String name;
	Thread t;
			
	private static AlertsService alertsService;
	private static ResultsService resultsService;

	@Autowired
	public MyThread(AlertsService theAlertsService,ResultsService theResultsService, Alerts theAlerts) {
		alertsService = theAlertsService;
		resultsService = theResultsService;
		
		alerts = theAlerts;
		name = alerts.getName();
		
		t = new Thread(this, name);
		System.out.println("New thread: " + t);
	}
	
	public void run() {
		System.out.println("\n" + name + " entering.");

		try{
			 getStatus(alerts.getUrl(),alerts.getHttp_method());
		}
		catch (Exception e) {
		     System.out.println(name + "Thread Exception");
		}
		 
		System.out.println(name + " exiting.\n");
		
	}
	
	public String getStatus(String url,String http_method)  {
		 
		String result = "";
		int code = 200;
		try {
			URL siteURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
			connection.setRequestMethod(http_method);
			connection.setConnectTimeout(1000);
			connection.connect();
 
			code = connection.getResponseCode();
			if (code == 200) {
				result = code  + " OK ";
				
				String alertsResult = alerts.getResult();
				
				alertsResult = alertsResult.substring(2, 11);
					
				alertsResult+=",1"; 
				
				alerts.setResult(alertsResult);
				
				//add results to the Alerts 
				Results results = new Results(1);
				resultsService.save(results);

				alerts.getResults().add(results);
		
				alertsService.save(alerts);
								
				System.out.println("Alerts result : "+ alertsResult);

				
				
			} else {
				result = " " +  code;
				
				String alertsResult = alerts.getResult();
				
				alertsResult = alertsResult.substring(2, 11);
				
				alertsResult+=",0" ;
				
				alerts.setResult(alertsResult);
				
				
				//add results to the Alerts 
				Results results = new Results(0);
				resultsService.save(results);

				alerts.getResults().add(results);
		
				alertsService.save(alerts);
				
				
				System.out.println("Alerts result : "+ alertsResult);


				
			}
		} catch (Exception e) {
			String alertsResult = alerts.getResult();
			
			alertsResult = alertsResult.substring(2, 11);
			
			alertsResult+=",0";
			
			alerts.setResult(alertsResult);
			
			//add results to the Alerts 
			Results results = new Results(0);
			resultsService.save(results);

			alerts.getResults().add(results);
	
			alertsService.save(alerts);
						
			System.out.println("Alerts result : "+ alertsResult);
			
			result = "Wrong domain - Exception: " + e.getMessage();
			
		}
		System.out.println(url + "\t\tStatus Code: " + result);
		return result;
	}
}