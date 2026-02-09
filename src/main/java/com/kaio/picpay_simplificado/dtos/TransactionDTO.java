package com.kaio.picpay_simplificado.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

// creates automatically a constructor, getters and other functions.
public record TransactionDTO(
    @NotNull(message = "O saldo deve ser obrigatoriamente informado.")
    BigDecimal value, 
    
    @NotNull(message = "O ID de quem está pagando é obrigatório.")
    Long senderId, 

    @NotNull(message = "O ID de quem irá receber é obrigatório.")
    Long receiverId) {

}
