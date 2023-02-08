package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.DepositDto;
import com.sogogo.bo.dto.DepositResp;
import com.sogogo.bo.model.Deposit;
import com.sogogo.bo.model.PaymentMethod;
import com.sogogo.bo.model.TransactionStatus;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.DepositRepo;
import com.sogogo.bo.repo.PaymentMethodRepo;
import com.sogogo.bo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/deposit")
public class DepositController {
    @Autowired
    private DepositRepo repo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    @PostMapping
    public BaseResponse<Long> submit(@RequestBody DepositDto dto,
                                       Authentication authentication){
        int error = 0;
        String msg = "Deposit success";
        Long id = null;
        try{
            String loginUsername = authentication.getName();
            UserSogogo user = userRepo.findByUsername(loginUsername).orElseThrow();
            PaymentMethod method = paymentMethodRepo.findById(dto.getPaymentMethod()).orElseThrow();
            Deposit deposit = new Deposit();
            deposit.setUser(user);
            deposit.setAmount(dto.getAmount());
            deposit.setStatus(TransactionStatus.PENDING);
            deposit.setPaymentMethod(method);
            deposit.setDescription(dto.getDescription());
            deposit.setDateAdd(Date.from(Instant.now()));
            Deposit saved = repo.save(deposit);
            id = saved.getId();
        }catch (Exception e){
            error = -1;
            msg = e.getMessage();
            log.error("Error: ", e);
        }

        return BaseResponse.<Long>builder()
                .error(error)
                .msg(msg)
                .data(id)
                .build();
    }

}
