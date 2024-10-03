package com.enigmacamp.barbershop.repository;

import com.enigmacamp.barbershop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String email);

}
