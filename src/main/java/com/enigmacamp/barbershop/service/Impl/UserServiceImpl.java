package com.enigmacamp.barbershop.service.Impl;

import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.UserRepository;
import com.enigmacamp.barbershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Users loadUserById(String id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("userId not found"));
    }
    @Override
    public Users getByContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Users loadUserByUsername(String email)  {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
