package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.WithdrawalResponse;

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
@Table(name = "m_withdrawals")
public class Withdrawals {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barbers barberId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "withdrawal_amount", nullable = false)
    private Double withdrawalAmount;

    @Column(name = "status", nullable = false)
    private String status;  

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    public WithdrawalResponse toResponse() {

        barberId.setOperationalHours(null);
        barberId.setServices(null);
        barberId.setSocial_media(null);

        return WithdrawalResponse.builder()
            .id(id)
            .barber(barberId.toResponse())
            .accountNumber(accountNumber)
            .accountName(accountName)
            .bankName(bankName)
            .withdrawalAmount(withdrawalAmount)
            .status(status)
            .createdAt(createdAt)
            .build();
    }
}
