package com.kaio.picpay_simplificado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kaio.picpay_simplificado.dtos.UserDTO;
import com.kaio.picpay_simplificado.dtos.UserResponseDTO;
import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    // @Valid uses the validations from the UserDTO
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO user) {
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(UserResponseDTO.from(newUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponseDTO> response = users.stream()
        .map(UserResponseDTO::from)
        .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
