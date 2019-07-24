package com.yte.springreact.alertingsystem;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.sending_email.SendingEmailService;
import com.yte.springreact.alertingsystem.service.AlertsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {
    private static int period=0;

    private static AlertsService alertsService;
    private static GetStatusService getStatusService;

    @Autowired
    public AppRunner(AlertsService theAlertsService, GetStatusService theGetStatusService) {
        alertsService = theAlertsService;
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

        //System.out.println("Period: " + period);
        for (Alerts alerts : listOfAlerts){
            if(period % alerts.getPeriod() != 0 )
                continue;

            //System.out.println("\n" + alerts.getName() + " entering.");

            getStatusService.getStatus(alerts);

            //Sending email operation
            List<Results> listOfResults = alerts.getResults();
            int length = listOfResults.size()-1;

            if(length>=1 && listOfResults.get(length).getStatus()==0 && listOfResults.get(length-1).getStatus()== 1 ){
                System.out.println("Sending email in scheduled");
                SendingEmailService.sendEmail(alerts, listOfResults.get(length));
            }

            //System.out.println(alerts.getName() + " exiting.\n");
        }
    }
}
