package com.example.sweater.controllers;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    UserService userService;
    private MockMvc mockMvc;
    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
        user = User
                .builder()
                .username("name").password("pass").active(true).id(1L)
                .build();
    }

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("registrationPage"));
    }

    @Test
    void addUserSuccessful() throws Exception {
        when(userService.addUser(any())).thenReturn(true);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "name")
                        .param("password", "pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void addUserWhenUserExists() throws Exception {
        when(userService.addUser(any())).thenReturn(false);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "name")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("registrationPage"));
    }
}