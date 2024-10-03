package com.enigmacamp.barbershop.repository;

import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbersProfilePictureRepository extends JpaRepository<BarberProfilePicture,String> {
    BarberProfilePicture getByName(String name);
}
