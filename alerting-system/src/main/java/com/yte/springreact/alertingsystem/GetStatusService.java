package com.yte.springreact.alertingsystem;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class GetStatusService {

    private static AlertsService alertsService;
    private static ResultsService resultsService;

    @Autowired
    public GetStatusService(AlertsService theAlertsService, ResultsService theResultsService) {
        alertsService = theAlertsService;
        resultsService = theResultsService;

    }

    @Async
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

                //Add date in results
                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedDate=dateFormat.format(date);

                results.setDate(formattedDate);
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
       // System.out.println(alert.getUrl() + "\t\tStatus Code: " + result);
    }
}
