package com.enigmacamp.barbershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_barber_profile_pictures")
public class BarberProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "created_at", nullable = false,updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;
}
