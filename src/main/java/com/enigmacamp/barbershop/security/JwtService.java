package com.enigmacamp.barbershop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.barbershop.model.dto.response.JwtUserInfo;
import com.enigmacamp.barbershop.model.entity.Users;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {
    // private final Stirng JWT_SECRET

    // Algorithm algorithm = Algorithm.HMAC256("rains");
    private final Algorithm algorithm = Algorithm.HMAC256("ndog".getBytes(StandardCharsets.UTF_8));

    public String generateToken(Users user) {
        try {
            return JWT
                    .create()
                    .withIssuer("jwt-secret")
                    .withSubject(user.getId())
                    // .withClaim("username", user.getUsername())
                    .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Jakarta")).plusMonths(1).toInstant())
                    // .withNotBefore(Instant.now().plusSeconds(3600))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("Error while creating JWT : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    public boolean verifyJwtToken(String token) {
        try {
            // DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(parseJwt(token));
            return true;
        } catch (JWTCreationException e) {
            log.error("Invalid JWT Signature/Claims : {}", e.getMessage());
            return false;
        }
    }

    // mengambil data account melalui token
    // cara 1 tanpa buat entity
    public Map<String, String> getUserInfoByToken(String bearerToken) {
        try {
            DecodedJWT decodedJWT = jwtVerifier().verify(parseJwt(bearerToken));

            Map<String, String> claims = new HashMap<>();
            claims.put("userId", decodedJWT.getSubject());
            claims.put("role", decodedJWT.getClaim("role").asString());

            return claims;
        } catch (JWTCreationException e) {
            log.error("Invalid JWT Signature/Claims : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    // cara 2 menggunakan entity
    public JwtUserInfo getUserInfoByToken2(String token) {
        DecodedJWT decodedJWT = jwtVerifier().verify(parseJwt(token));

        return JwtUserInfo.builder()
                .userId(decodedJWT.getId())
                .roles(decodedJWT.getClaim("roles").asList(String.class))
                .build();
    }

    private String parseJwt(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    private JWTVerifier jwtVerifier() {
        return JWT.require(algorithm)
                .build();
    }
}
