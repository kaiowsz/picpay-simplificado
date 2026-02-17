package com.kaio.picpay_simplificado.services;

import com.kaio.picpay_simplificado.services.UserService;
import com.kaio.picpay_simplificado.services.NotificationService;
import com.kaio.picpay_simplificado.services.TransactionService;

// Imports de Domínio e DTOs
import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.models.UserType;
import com.kaio.picpay_simplificado.dtos.TransactionDTO;
import com.kaio.picpay_simplificado.repository.TransactionRepository;

// Imports de Teste (JUnit e Mockito)
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private UserService userService;    

    @Mock
    private TransactionRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar transação com sucesso quando tudo estiver ok")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L, "Maria", "Souza", "99999999901", "maria@gmail.com", "12345", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "Joao", "Silva", "88888888801", "joao@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);

        when(userService.findUserById(2L)).thenReturn(receiver);

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(Map.of("status", "success"), HttpStatus.OK));

        
        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        verify(notificationService, times(1)).sendNotification(sender, "Sua transferência foi realizada com sucesso.");

        verify(notificationService, times(1)).sendNotification(receiver, "Você recebeu uma transferência.");
    }

    @Test
    @DisplayName("Deve lançar Exception quando a transação não for autorizada")
    void createTransactionCase2() throws Exception {
        User sender = new User(1L, "Maria", "...", "...", "...", "...", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "Joao", "...", "...", "...", "...", new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(Map.of("message", "Negado"), HttpStatus.OK));
        
        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada.", thrown.getMessage());
    }

    
}
