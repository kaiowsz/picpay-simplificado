package com.kaio.picpay_simplificado.dtos;
import com.kaio.picpay_simplificado.models.UserType;

public record RegisterDTO(String login, String password, String document, UserType role) {
    
}
