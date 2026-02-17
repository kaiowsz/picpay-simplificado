package com.kaio.picpay_simplificado.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.kaio.picpay_simplificado.models.Transaction;

public record TransactionResponseDTO(
    Long id,
    BigDecimal value,
    UserResponseDTO sender,
    UserResponseDTO receiver,
    LocalDateTime timestamp
) {
    public static TransactionResponseDTO from(Transaction t) {

        return new TransactionResponseDTO(
            t.getId(), 
            t.getAmount(), 
            UserResponseDTO.from(t.getSender()), 
            UserResponseDTO.from(t.getReceiver()),
            t.getTimestamp()
        );

    }
}
