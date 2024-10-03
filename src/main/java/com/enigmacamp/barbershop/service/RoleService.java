package com.enigmacamp.barbershop.service;

import com.enigmacamp.barbershop.constant.UserRole;
import com.enigmacamp.barbershop.model.entity.Role;

public interface RoleService {
    Role getOrCreate(UserRole role);
}
