package com.enigmacamp.barbershop.repository;

import java.util.List;
import java.util.Optional;

import com.enigmacamp.barbershop.model.entity.Barbers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enigmacamp.barbershop.model.entity.Users;

@Repository
public interface BarbersRepository extends JpaRepository<Barbers, String> {
    Barbers findByEmail(String email);

    Optional<Barbers> findByUserId(Users user);

    @Query(value = "SELECT id, name, street_address, city, state_province_region, country, latitude, longitude, balance, barbershop_profile_picture_id, contact_number, created_at, description, email, "
            +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + "
            +
            "sin(radians(:latitude)) * sin(radians(latitude))) AS distance_km " +
            "FROM m_barbers " +
            "HAVING distance_km <= :distance " +
            "ORDER BY distance_km ASC", nativeQuery = true)
    List<Barbers> findNearbyBarbers(@Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance);
}
