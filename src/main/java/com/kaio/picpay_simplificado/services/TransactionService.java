package com.kaio.picpay_simplificado.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.kaio.picpay_simplificado.dtos.TransactionDTO;
import com.kaio.picpay_simplificado.infra.TransactionException;
import com.kaio.picpay_simplificado.models.User;

import com.kaio.picpay_simplificado.models.Transaction;
import com.kaio.picpay_simplificado.repository.TransactionRepository;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = userService.findUserById(transaction.senderId());

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var userAuthenticated = (User) authentication.getPrincipal();

        if(!sender.getId().equals(userAuthenticated.getId())) {
            throw new Exception("Transação não autorizada: você não pode movimentar a conta de outro usuário.");
        }

        User receiver = userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());

        if(!isAuthorized) {
            throw new TransactionException("Transação não autorizada.");
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(receiver, "Você recebeu uma transferência.");

        this.notificationService.sendNotification(sender, "Sua transferência foi realizada com sucesso.");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {

        String url = "https://util.devi.tools/api/v2/authorize";

        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);
            
            if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
                String message = (String) authorizationResponse.getBody().get("status");
                return "success".equalsIgnoreCase(message);
            } else return false;

        } catch (Exception e) {
            System.out.println("Erro no TransactionService: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
