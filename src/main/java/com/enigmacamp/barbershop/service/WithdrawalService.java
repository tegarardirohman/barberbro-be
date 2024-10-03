package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Withdrawals;

public interface WithdrawalService {
    Withdrawals create(Withdrawals withdrawals);

    Withdrawals update(Withdrawals withdrawals);

    List<Withdrawals> getAll();

    List<Withdrawals> getAllByBarber(Barbers barber);

    Withdrawals getById(String id);

}