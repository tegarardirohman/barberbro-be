package com.enigmacamp.barbershop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_gallery_images")
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String image_id;

    @ManyToOne
    @JoinColumn(name = "barbers_id")
    private Barbers barbersId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;
}