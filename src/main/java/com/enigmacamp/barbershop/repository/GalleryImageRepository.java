package com.enigmacamp.barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.GalleryImage;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, String> {
    List<GalleryImage> findAllByBarbersId(Barbers barbersId);
}