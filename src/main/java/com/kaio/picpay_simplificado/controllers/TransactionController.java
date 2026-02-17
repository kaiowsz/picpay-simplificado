package com.kaio.picpay_simplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaio.picpay_simplificado.dtos.DepositDTO;
import com.kaio.picpay_simplificado.dtos.TransactionDTO;
import com.kaio.picpay_simplificado.dtos.TransactionResponseDTO;
import com.kaio.picpay_simplificado.dtos.UserResponseDTO;
import com.kaio.picpay_simplificado.models.Transaction;
import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.services.TransactionService;
import com.kaio.picpay_simplificado.services.UserService;

import jakarta.validation.Valid;

@RestController // API route
@RequestMapping("/transactions") // base address localhost:8080/transactions
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping // accept POST request
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid TransactionDTO transaction) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transaction);

        var response = TransactionResponseDTO.from(newTransaction);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<UserResponseDTO> deposit(@RequestBody @Valid DepositDTO data) throws Exception {
        User updatedUser = this.userService.deposit(data);

        return new ResponseEntity<>(UserResponseDTO.from(updatedUser), HttpStatus.OK);
    }
    
}