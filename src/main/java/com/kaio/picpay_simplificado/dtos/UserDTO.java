package com.kaio.picpay_simplificado.dtos;

import com.kaio.picpay_simplificado.models.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UserDTO(
    @NotBlank(message = "O nome não pode estar vazio.")
    String firstName,
    
    @NotBlank(message = "O sobrenome não pode estar vazio.")
    String lastName,
    
    @NotBlank(message = "O documento (CPF/CNPJ) é obrigatório.")
    String document,
    
    @NotNull(message = "O saldo inicial é obrigatório.")
    BigDecimal balance,
    
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    String email,
    
    @NotBlank(message = "A senha é obrigatória.")
    String password,

    @NotNull(message = "O tipo de usuário (COMMON/MERCHANT) é obrigatório")
    UserType userType
) {
    
}
