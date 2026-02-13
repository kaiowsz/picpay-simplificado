package com.kaio.picpay_simplificado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaio.picpay_simplificado.models.User;

// <User, Long> refers to the entity and the ID type respectively
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(Long id);
}
