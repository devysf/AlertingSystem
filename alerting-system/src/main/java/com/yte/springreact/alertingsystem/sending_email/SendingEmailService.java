package com.yte.springreact.alertingsystem.sending_email;

import javax.mail.internet.MimeMessage;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendingEmailService {

    private static JavaMailSender sender;

    private static AlertsService alertsService;
    private static ResultsService resultsService;

    @Autowired
    public SendingEmailService(JavaMailSender sender,AlertsService alertsService,ResultsService resultsService ){
        this.sender = sender;
        this.alertsService = alertsService;
        this.resultsService = resultsService;
    }

    public SendingEmailService(){

    }

    @Scheduled(fixedRate = 1000)
    public void controlAlertsIsUpDown() {

        List<Alerts> listOfAlerts = alertsService.findAll();

        for (Alerts alerts : listOfAlerts) {
            System.out.println("Alerts "+ alerts.getName() + " Result" + alerts.getResults().size());
            List<Results> listOfResults = alerts.getResults();
            int length = listOfResults.size()-1;

            if(listOfResults.get(length).getStatus()==0 && listOfResults.get(length-1).getStatus()== 1 ){
                System.out.println("Sending email in scheduled");
                sendEmail(alerts, listOfResults.get(length));
            }
        }
    }


    public static String sendEmail(Alerts alerts,Results results){
        System.out.println(sender);
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo("ozacetyusuf@gmail.com");
            helper.setText("Your website " + alerts.getName() + " is become down at " + results.getDate());
            helper.setSubject("ALERT!!!" );

            sender.send(message);
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }
}