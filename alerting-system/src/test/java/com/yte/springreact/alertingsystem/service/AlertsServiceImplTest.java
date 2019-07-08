package com.yte.springreact.alertingsystem.service;

import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertsServiceImplTest {

    @Mock
    AlertsRepository alertsRepository;

    @InjectMocks
    AlertsService alertsService =  new AlertsServiceImpl(alertsRepository);


    @Test
    public void findAll() {
        //given
        Alerts alerts = new Alerts();
        List<Alerts> alertsList = new ArrayList<>();
        alertsList.add(alerts);

        given(alertsRepository.findAll()).willReturn(alertsList);

        //when
        List<Alerts> foundAlerts = alertsService.findAll();

        //then
        then(alertsRepository).should().findAll();
        assertThat(foundAlerts).hasSize(1);
    }

    @Test
    public void findById() {
        //given
        Alerts alerts = new Alerts();
        given(alertsRepository.findById(anyInt())).willReturn(Optional.of(alerts));

        //when
        Alerts foundAlerts = alertsService.findById(1);

        //then
        then(alertsRepository).should().findById(anyInt());
        assertThat(foundAlerts).isNotNull();
    }

    @Test
    public void save() {
        //given
        Alerts alerts = new Alerts();
        given(alertsRepository.save(any(Alerts.class))).willReturn(alerts);

        //when
        Alerts savedAlerts = alertsService.save(new Alerts());

        //then
        then(alertsRepository).should().save(any(Alerts.class));
        assertThat(savedAlerts).isNotNull();
    }

    @Test
    public void deleteById() {
        //given - none because deleteById is a void function

        //when
        alertsService.deleteById(1);

        //then
        then(alertsRepository).should().deleteById(anyInt());

    }
}