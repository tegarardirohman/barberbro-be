package com.enigmacamp.barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Withdrawals;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawals, String> {

    List<Withdrawals> getAllByBarberId(Barbers barber);
}