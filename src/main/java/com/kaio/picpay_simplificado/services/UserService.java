package com.kaio.picpay_simplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaio.picpay_simplificado.dtos.DepositDTO;
import com.kaio.picpay_simplificado.dtos.UserDTO;
import com.kaio.picpay_simplificado.models.User;
import com.kaio.picpay_simplificado.models.UserType;
import com.kaio.picpay_simplificado.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transações.");
        }

        // must use .compareTo when the type is BigDecimal. if it's lower, the function returns -1.
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id)
        .orElseThrow(() -> new Exception("Usuario nao encontrado."));
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        
        this.saveUser(newUser);

        return newUser;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    public User deposit(DepositDTO data) throws Exception {
        User user = findUserById(data.userId());

        user.setBalance(user.getBalance().add(data.value()));

        this.saveUser(user);

        return user;
    } 
}
