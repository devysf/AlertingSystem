package com.yte.springreact.alertingsystem.service;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.entity.Results;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;
import com.yte.springreact.alertingsystem.repository.ResultsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class ResultsServiceImplTest {

    @Mock
    ResultsRepository resultsRepository;

    @InjectMocks
    ResultsService resultsService = new ResultsServiceImpl(resultsRepository);

    @Test
    public void findAll() {
        //given
        Results results = new Results();
        List<Results> resultsList = new ArrayList<>();
        resultsList.add(results);

        given(resultsRepository.findAll()).willReturn(resultsList);

        //when
        List<Results> foundResults = resultsService.findAll();

        //then
        then(resultsRepository).should().findAll();
        assertThat(foundResults).hasSize(1);
    }

    @Test
    public void findById() {
        //given
        Results results = new Results();

        given(resultsRepository.findById(anyInt())).willReturn(Optional.of(results));

        //when
        Results foundResults = resultsService.findById(1);

        //then
        then(resultsRepository).should().findById(anyInt());
        assertThat(foundResults).isNotNull();
    }


    @Test
    public void save() {
        //given
        Results results = new Results();
        given(resultsRepository.save(any(Results.class))).willReturn(results);

        //when
        Results savedResults = resultsService.save(new Results());

        //then
        then(resultsRepository).should().save(any(Results.class));
        assertThat(savedResults).isNotNull();
    }

    @Test
    public void deleteById() {
        //given - none because deleteById is a void function

        //when
        resultsService.deleteById(1);

        //then
        then(resultsRepository).should().deleteById(anyInt());

    }

}