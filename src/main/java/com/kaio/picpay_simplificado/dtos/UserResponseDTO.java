package com.kaio.picpay_simplificado.dtos;

import java.math.BigDecimal;

import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.models.UserType;

public record UserResponseDTO(Long id, String firstName, String lastName,
    String document, String email, BigDecimal balance, UserType userType
) {
    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getDocument(),
            user.getEmail(),
            user.getBalance(),
            user.getUserType()
        );
    }
}
