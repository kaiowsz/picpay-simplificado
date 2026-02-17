package com.kaio.picpay_simplificado.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.kaio.picpay_simplificado.models.Transaction;

public record TransactionResponseDTO(
    Long id,
    BigDecimal value,
    Long senderId,
    Long receiverId,
    LocalDateTime timestamp
) {
    public TransactionResponseDTO(Transaction t) {
        this(
            t.getId(), 
            t.getAmount(), 
            t.getSender().getId(),
            t.getReceiver().getId(), 
            t.getTimestamp()
        );
    }
}
