package com.enigmacamp.barbershop.model.entity;

import java.util.List;

import com.enigmacamp.barbershop.model.dto.response.BarberResponse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_barbers")
public class Barbers {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "contact_number", nullable = false)
        private String contact_number;

        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @Column(name = "street_address", nullable = false)
        private String street_address;

        @Column(name = "city", nullable = false)
        private String city;

        @Column(name = "state_province_region", nullable = false)
        private String state_province_region;

        @Column(name = "postal_zip_code", nullable = false)
        private String postal_zip_code;

        @Column(name = "country", nullable = false)
        private String country;

        @Column(name = "latitude", nullable = false)
        private Double latitude;

        @Column(name = "longitude", nullable = false)
        private Double longitude;

        @Column(name = "description")
        private String description;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private Users userId;

        @OneToMany(mappedBy = "barberId")
        private List<Booking> bookings;

        @Column(name = "balance", nullable = false)
        private float balance;

        @Column(name = "verified", nullable = false)
        private Boolean verified;

        @ManyToOne
        @JoinColumn(name = "barbershop_profile_picture_id")
        private BarberProfilePicture barbershop_profile_picture_id;

        @OneToMany(mappedBy = "barbershop_id")
        private List<OperationalHour> operationalHours;

        @OneToMany(mappedBy = "barbershop_id")
        private List<Service> services;

        @OneToMany(mappedBy = "barbershop_id")
        private List<SocialMedia> social_media;

        @OneToMany(mappedBy = "barbershopId")
        private List<Review> reviews;

        @Column(name = "created_at", nullable = false, updatable = false)
        private Long createdAt;

        @Column(name = "updated_at", nullable = false)
        private Long updateAt;

        @Column(name = "deleted_at", nullable = true)
        private Long deletedAt;

        public BarberResponse toResponse() {
                return BarberResponse.builder()
                                .id(this.id)
                                .name(this.name)
                                .contact_number(this.contact_number)
                                .email(this.email)
                                .street_address(this.street_address)
                                .city(this.city)
                                .state_province_region(this.state_province_region)
                                .postal_zip_code(this.postal_zip_code)
                                .country(this.country)
                                .latitude(this.latitude)
                                .longitude(this.longitude)
                                .description(this.description)
                                // .userId(this.userId)
                                // .balance(this.balance)
                                .verified(this.verified)
                                .barbershop_profile_picture_id(this.barbershop_profile_picture_id)
                                .operational_hours(this.operationalHours == null ? null
                                                : this.operationalHours.stream().map(OperationalHour::toResponse)
                                                                .toList())
                                .services(this.services == null ? null
                                                : services.stream().map(Service::toResponse).toList())
                                .social_media(
                                                this.social_media == null ? null
                                                                : social_media.stream().map(SocialMedia::toResponse)
                                                                                .toList())
                                .createdAt(this.createdAt)
                                .updateAt(this.updateAt)
                                .build();
        }

}