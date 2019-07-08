package com.yte.springreact.alertingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;

@Service
public class AlertsServiceImpl implements AlertsService{


    private AlertsRepository alertsRepository;

    @Autowired
    public AlertsServiceImpl(AlertsRepository theAlertsRepository) {
        alertsRepository = theAlertsRepository;
    }


    @Override
    public List<Alerts> findAll() {
        return alertsRepository.findAll();
    }


    @Override
    public Alerts findById(int alertsId) {
        Optional<Alerts> result = alertsRepository.findById(alertsId);

        Alerts theAlerts = null;

        if (result.isPresent()) {
            theAlerts = result.get();
        } else {
            throw new RuntimeException("Did not find Alerts id - " + alertsId);

        }
        return theAlerts;
    }


    @Override
    public Alerts save(Alerts theAlerts) {
        return alertsRepository.save(theAlerts);
    }


    @Override
    public void deleteById(int alertsId) {
        alertsRepository.deleteById(alertsId);
    }


}
