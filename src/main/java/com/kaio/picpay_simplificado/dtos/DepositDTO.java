package com.kaio.picpay_simplificado.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositDTO(
    @NotNull Long userId,
    @NotNull @Positive BigDecimal value
) {
    
}
