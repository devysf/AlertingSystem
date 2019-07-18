package com.yte.springreact.alertingsystem.sending_email;

import javax.mail.internet.MimeMessage;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.ResultsService;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendingEmailService {

    private static JavaMailSender sender;

    private static AlertsService alertsService;
    private static ResultsService resultsService;
    private static UserService userService;
    @Autowired
    public SendingEmailService(JavaMailSender sender,AlertsService alertsService,ResultsService resultsService ,UserService userService){
        this.sender = sender;
        this.alertsService = alertsService;
        this.resultsService = resultsService;
        this.userService = userService;
    }

    public static String sendEmail(Alerts alerts,Results results){
        System.out.println(sender);
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            User foundUser = userService.findByUsername(alerts.getCreatedBy());
            helper.setTo(foundUser.getEmail());
            helper.setText("Your website " + alerts.getName() + " is become down at " + results.getDate());
            helper.setSubject("ALERT!!!" );

            sender.send(message);
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }
}