package com.vinsguru.payment.controller;
import com.vinsguru.dto.OrderRequestDto;
import com.vinsguru.payment.entity.UserTransaction;
import com.vinsguru.payment.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class UserBalanceController {

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @GetMapping("/all")
    public List<UserTransaction> getOrders(){
        return this.userTransactionRepository.findAll();
    }

}
