package com.kaio.picpay_simplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaio.picpay_simplificado.dtos.TransactionDTO;
import com.kaio.picpay_simplificado.services.TransactionService;

import jakarta.validation.Valid;

@RestController // API route
@RequestMapping("/transactions") // base address localhost:8080/transactions
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping // accept POST request
    public ResponseEntity<Void> createTransaction(@RequestBody @Valid TransactionDTO transaction) throws Exception {
        this.transactionService.createTransaction(transaction);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}