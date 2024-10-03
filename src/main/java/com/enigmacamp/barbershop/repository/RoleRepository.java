package com.enigmacamp.barbershop.repository;

import com.enigmacamp.barbershop.constant.UserRole;
import com.enigmacamp.barbershop.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);

}
