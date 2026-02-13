package com.kaio.picpay_simplificado.controllers;

import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.models.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.picpay_simplificado.dtos.TransactionDTO;
import com.kaio.picpay_simplificado.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve realizar transação com sucesso")
    void createTransactionCase1() throws Exception {
        User sender = new User(null, "Maria", "Souza", "99999999901", "maria@gmail.com", "12345", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(null, "Joao", "Silva", "88888888801", "joao@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

        userRepository.save(sender);
        userRepository.save(receiver);

        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(new ResponseEntity<>(Map.of("status", "success"), HttpStatus.OK));

        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(new ResponseEntity<>("Notificado", HttpStatus.OK));

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), sender.getId(), receiver.getId());

        mockMvc
        .perform(post("/transactions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }
}