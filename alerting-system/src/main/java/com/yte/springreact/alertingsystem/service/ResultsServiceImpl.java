package com.yte.springreact.alertingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;
import com.yte.springreact.alertingsystem.repository.ResultsRepository;

@Service
public class ResultsServiceImpl implements ResultsService{


    private ResultsRepository resultsRepository;

    @Autowired
    public ResultsServiceImpl(ResultsRepository theAlertsRepository) {
        resultsRepository = theAlertsRepository;
    }


    @Override
    public List<Results> findAll() {
        return resultsRepository.findAll();
    }


    @Override
    public Results findById(int alertsId) {
        Optional<Results> result = resultsRepository.findById(alertsId);

        Results theResults = null;

        if (result.isPresent()) {
            theResults = result.get();
        } else {
            throw new RuntimeException("Did not find Alerts id - " + alertsId);

        }
        return theResults;
    }


    @Override
    public Results save(Results theResults) {
        return resultsRepository.save(theResults);
    }


    @Override
    public void deleteById(int resultsId) {
        resultsRepository.deleteById(resultsId);
    }


}
