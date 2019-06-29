package com.yte.springreact.alertingsystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;

class MyThread implements Runnable {
	Alerts alerts;
	String name;
	Thread t;
		
	private static AlertsService alertsService;
	
	@Autowired
	public MyThread(AlertsService theAlertsService, Alerts theAlerts) {
		alertsService = theAlertsService;
		
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
		     System.out.println(name + "Exception");
		}
		 
		System.out.println(name + " exiting.\n");
		
	}
	
	public String getStatus(String url,String http_method) throws IOException {
		 
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
				
				alertsResult = alertsResult.substring(1, 6);
					
				alertsResult+="1"; 

				alerts.setResult(alertsResult);
				
				alertsService.save(alerts);
				
				System.out.println("Alerts result : "+ alertsResult);

				
				
			} else {
				result = " " +  code;
				
				String alertsResult = alerts.getResult();
				
				alertsResult = alertsResult.substring(1, 6);
				
				alertsResult+="0" ;
				
				alerts.setResult(alertsResult);
				
				alertsService.save(alerts);
				
				System.out.println("Alerts result : "+ alertsResult);


				
			}
		} catch (Exception e) {
			result = "Wrong domain - Exception: " + e.getMessage();
			
		}
		System.out.println(url + "\t\tStatus Code: " + result);
		return result;
	}
}