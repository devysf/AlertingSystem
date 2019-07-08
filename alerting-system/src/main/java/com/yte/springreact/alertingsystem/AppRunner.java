package com.yte.springreact.alertingsystem;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {
    private static int period=0;

    private static AlertsService alertsService;
    private static ResultsService resultsService;
    private static GetStatusService getStatusService;

    @Autowired
    public AppRunner(AlertsService theAlertsService, ResultsService theResultsService,GetStatusService theGetStatusService) {
        alertsService = theAlertsService;
        resultsService = theResultsService;
        getStatusService = theGetStatusService;
    }

    @Override
    public void run(String... args) throws Exception {
        //This code blocks are running before spring application run.
    }

    @Scheduled(fixedRate = 1000)
    public void runProgram(){
        period+=1000;

        List<Alerts> listOfAlerts = alertsService.findAll();

        System.out.println("Period: " + period);

        for (Alerts alerts : listOfAlerts){
            if(period % alerts.getPeriod() != 0 )
                continue;

            System.out.println("\n" + alerts.getName() + " entering.");

            getStatusService.getStatus(alerts);

            System.out.println(alerts.getName() + " exiting.\n");
        }
    }

}
