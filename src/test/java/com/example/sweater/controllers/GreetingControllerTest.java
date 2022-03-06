package com.example.sweater.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class GreetingControllerTest {

    private GreetingController greetingController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        greetingController = new GreetingController();
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
    }

    @Test
    void getGreeting() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("name"))
                .andExpect(view().name("greetingPage"));
    }
}