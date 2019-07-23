package com.yte.springreact.alertingsystem.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yte.springreact.alertingsystem.entity.Alerts;
import com.yte.springreact.alertingsystem.repository.AlertsRepository;
import com.yte.springreact.alertingsystem.service.AlertsService;
import com.yte.springreact.alertingsystem.service.AlertsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
public class AlertsRestControllerTest {

    @Mock
    AlertsRepository alertsRepository;

    @Mock
    AlertsService alertsService;

    @InjectMocks
    AlertsRestController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void sayHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/api/alerts"))
                .andExpect(status().isOk());

        then(alertsService).should().findAll();
    }

    @Test
    public void getAlerts() throws Exception {
        Alerts alerts = new Alerts();
        alerts.setId(1);

        given(alertsService.findById(1)).willReturn(alerts);

        mockMvc.perform(get("/api/alerts/{alertsId}",1)
                .param("name", "Youtube"))
                .andExpect(status().isOk());

        then(alertsService).should().findById(1);
    }

    @Test
    public void addAlerts() throws Exception {
        Alerts alerts =new Alerts();
        alerts.setId(1);
        alerts.setName("Youtube");

        given(alertsService.save(any(Alerts.class))).willReturn(alerts);

        mockMvc.perform(post("/api/alerts")
                .content(asJsonString(new Alerts("Youtube", "youtube.com", "GET",2000)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());


        then(alertsService).should().save(any(Alerts.class));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateAlerts() throws Exception {
        given(alertsService.save(any(Alerts.class))).willReturn(new Alerts());


        mockMvc.perform(put("/api/alerts")
                .content(asJsonString(new Alerts("Youtube", "youtube.com", "GET",2000)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Youtube"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("youtube.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_method").value("GET"));

        then(alertsService).should().save(any(Alerts.class));

    }

    @Test
    public void deleteAlerts() throws Exception {
        given(alertsService.findById(anyInt())).willReturn(new Alerts());

        mockMvc.perform(delete("/api/alerts/{alertsId}",1))
                .andExpect(status().isOk());

        then(alertsService).should().findById(anyInt());
        then(alertsService).should().deleteById(anyInt());
    }
}