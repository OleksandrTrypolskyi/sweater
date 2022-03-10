package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    public static final String INDEX = "/index";
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private IndexController indexController;
    private MockMvc mockMvc;
    private Message message;
    private List<Message> messages;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        message = Message.builder().text("text").tag("tag").build();
        messages = List.of(message);
    }

    @Test
    void index() throws Exception {
        when(messageRepository.findAll()).thenReturn(messages);

        mockMvc.perform(get(INDEX))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("messages"))
                .andExpect(view().name("main"));
    }

    @Test
    void addMessage() throws Exception {
        when(messageRepository.save(any())).thenReturn(message);
        when(messageRepository.findAll()).thenReturn(messages);

        mockMvc.perform(post(INDEX)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content("")
                        .param("text", "text")
                        .param("tag", "tag"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(view().name("main"));
    }

    @Test
    void findMessages() throws Exception {
        when(messageRepository.findAllByTextContainingIgnoreCase(any())).thenReturn(messages);

        mockMvc.perform(get(INDEX)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("filter", "text"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attributeExists("filter"))
                .andExpect(view().name("main"));

        verify(messageRepository, times(1)).findAllByTextContainingIgnoreCase(any());
    }


    @Test
    void findMessagesWithEmptyParameter() throws Exception {
        when(messageRepository.findAll()).thenReturn(messages);

        mockMvc.perform(get(INDEX)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("filter", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attributeExists("filter"))
                .andExpect(view().name("main"));

        verify(messageRepository, times(1)).findAll();
    }

}