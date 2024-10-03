package com.enigmacamp.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.barbershop.model.entity.SocialMedia;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, String> {
}