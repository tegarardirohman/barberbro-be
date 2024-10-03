package com.enigmacamp.barbershop.service.Impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.constant.WithdrawalStatus;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Withdrawals;
import com.enigmacamp.barbershop.repository.BarbersRepository;
import com.enigmacamp.barbershop.repository.WithdrawalRepository;
import com.enigmacamp.barbershop.service.WithdrawalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final BarbersRepository barbersRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Withdrawals create(Withdrawals withdrawals) {
        try {
            return withdrawalRepository.save(withdrawals);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public List<Withdrawals> getAll() {
        return withdrawalRepository.findAll();
    }

    @Override
    public List<Withdrawals> getAllByBarber(Barbers barber) {
        return withdrawalRepository.getAllByBarberId(barber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Withdrawals update(Withdrawals withdrawals) {
        try {
            Withdrawals data = withdrawalRepository.save(withdrawals);

            if (data.getStatus() == null ? WithdrawalStatus.APPROVED.name() == null : data.getStatus().equals(WithdrawalStatus.APPROVED.name())) {
                Barbers barbers = data.getBarberId();

                if (barbers == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barber not found");
                }

                barbers.setBalance((float) ((float) barbers.getBalance() - data.getWithdrawalAmount()));

                barbersRepository.save(barbers);

                return data;
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Withdrawal not approved");
            
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Withdrawals getById(String id) {
        return withdrawalRepository.findById(id).orElse(null);
    }

}