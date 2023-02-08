package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.DepositResp;
import com.sogogo.bo.model.Deposit;
import com.sogogo.bo.model.TransactionStatus;
import com.sogogo.bo.repo.DepositRepo;
import com.sogogo.bo.repo.PaymentMethodRepo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/deposit/list")
public class DepositListController {

    @Autowired
    private DepositRepo repo;

    @GetMapping
    public BaseResponse<List<DepositResp>> list(
            @RequestParam(required = false) Integer status,
            Authentication auth
    ){
        List<Deposit> deposits = repo.findAllByStatus(status);

        List<DepositResp> resp = deposits.stream().map(row ->
                DepositResp.builder()
                        .id(row.getId())
                        .userId(row.getUser().getId())
                        .username(row.getUser().getUsername())
                        .amount(row.getAmount())
                        .status(row.getStatus())
                        .description(row.getDescription())
                        .paymentMethod(row.getPaymentMethod().getId())
                        .paymentMethodName(row.getPaymentMethod().getName())
                        .dateAdd(row.getDateAdd())
                        .build()).collect(Collectors.toList());
        return BaseResponse.<List<DepositResp>>builder()
                .data(resp)
                .build();
    }

}
