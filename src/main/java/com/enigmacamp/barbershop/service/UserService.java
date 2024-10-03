package com.enigmacamp.barbershop.service;

import com.enigmacamp.barbershop.model.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Users loadUserByUsername(String email);
    Users loadUserById(String id);
    Users getByContext();

}
