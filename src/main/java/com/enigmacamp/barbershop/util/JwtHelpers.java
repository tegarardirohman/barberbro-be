package com.enigmacamp.barbershop.util;

import org.springframework.stereotype.Component;

import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHelpers {
    private final AuthTokenExtractor authTokenExtractor;
    private final UserService userService;

    public Users getUser(HttpServletRequest request) {

        String userId = authTokenExtractor.getTokenFromHeader(request);

        if (userId != null) {
            return userService.loadUserById(userId);
        }

        return null;
    }
}