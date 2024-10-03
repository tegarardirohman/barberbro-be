package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.CustomerResponse;

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
@Table(name = "m_customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "surname", nullable = true)
    private String surname;

    @Column(name = "isMale", nullable = true)
    private Boolean isMale;

    @Column(name = "date_of_birth", nullable = true)
    private Long dateOfBirth;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "phone", nullable = true)
    private String phone;

    private float balance;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "about", nullable = true)
    private String about;

    public CustomerResponse toResponse() {
        return CustomerResponse.builder()
                .id(id)
                .firstName(firstName)
                .surname(surname)
                .isMale(isMale)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .phone(phone)
                .address(address)
                .about(about)
                .build();
    }
}
