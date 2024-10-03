package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.ServiceResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String service_id;

    @ManyToOne
    @JoinColumn(name = "barbershop_id", referencedColumnName = "id", nullable = false)
    private Barbers barbershop_id;

    private String service_name;
    private Double price;

    public ServiceResponse toResponse() {
        return ServiceResponse.builder()
                .service_id(this.service_id)
                .service_name(this.service_name)
                .price(this.price)
                .build();
    }
}