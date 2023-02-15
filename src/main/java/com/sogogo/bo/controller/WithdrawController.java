package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.WithdrawDto;
import com.sogogo.bo.model.*;
import com.sogogo.bo.repo.PaymentMethodRepo;
import com.sogogo.bo.repo.UserRepository;
import com.sogogo.bo.repo.WithdrawRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.Instant;

@RestController
@RequestMapping("withdraw")
@Slf4j
public class WithdrawController {

    @Autowired
    private WithdrawRepo repo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    @PostMapping
    public BaseResponse<Long> submit(@RequestBody WithdrawDto dto,
                                     Authentication authentication){
        int error = 0;
        String msg = "Withdraw success";
        Long id = null;
        try{
            String loginUsername = authentication.getName();
            UserSogogo user = userRepo.findByUsername(loginUsername).orElseThrow();
            PaymentMethod method = paymentMethodRepo.findById(dto.getPaymentMethod()).orElseThrow();
            Withdraw entity = new Withdraw();
            entity.setUser(user);
            entity.setAmount(dto.getAmount());
            entity.setStatus(TransactionStatus.PENDING);
            entity.setPaymentMethod(method);
            entity.setDescription(dto.getDescription());
            entity.setDateAdd(Date.from(Instant.now()));
            Withdraw saved = repo.save(entity);
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
