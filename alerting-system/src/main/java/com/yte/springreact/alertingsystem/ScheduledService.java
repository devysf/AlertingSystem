package com.yte.springreact.alertingsystem;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class ScheduledService {
    private static int period=0;

    private static AlertsService alertsService;
    private static ResultsService resultsService;


    @Autowired
    public ScheduledService(AlertsService theAlertsService,ResultsService theResultsService) {
        alertsService = theAlertsService;
        resultsService = theResultsService;

    }

    @Scheduled(fixedDelay = 1000)
    public void runProgram(){
        List<Alerts> listOfAlerts = alertsService.findAll();

        period+=1000;
        System.out.println("Period: " + period);

        for (Alerts alerts : listOfAlerts){
            if(period % alerts.getPeriod() != 0 )
                continue;

            System.out.println("\n" + alerts.getName() + " entering.");

            getStatus(alerts);

            System.out.println(alerts.getName() + " exiting.\n");
        }

    }


    public void getStatus(Alerts alert)  {

        String result = "";
        int code = 200;
        try {
            URL siteURL = new URL(alert.getUrl());
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod(alert.getHttp_method());
            connection.setConnectTimeout(1000);
            connection.connect();

            code = connection.getResponseCode();
            if (code == 200) {
                result = code  + " OK ";

                //add results to the Alerts
                Results results = new Results(1);
                resultsService.save(results);

                alert.getResults().add(results);

                alertsService.save(alert);

            } else {
                result = " " +  code;

                //add results to the Alerts
                Results results = new Results(0);
                resultsService.save(results);

                alert.getResults().add(results);

                alertsService.save(alert);
            }
        } catch (Exception e) {
            //add results to the Alerts
            Results results = new Results(0);
            resultsService.save(results);

            alert.getResults().add(results);

            alertsService.save(alert);

            result = "Wrong domain - Exception: " + e.getMessage();

        }
        System.out.println(alert.getUrl() + "\t\tStatus Code: " + result);
    }
}
