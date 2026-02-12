package com.kaio.picpay_simplificado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaio.picpay_simplificado.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
