package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.DepositResp;
import com.sogogo.bo.dto.WithdrawResp;
import com.sogogo.bo.model.Deposit;
import com.sogogo.bo.model.Withdraw;
import com.sogogo.bo.repo.DepositRepo;
import com.sogogo.bo.repo.WithdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("withdraw/list")
public class WithdrawListController {

    @Autowired
    private WithdrawRepo repo;

    @GetMapping
    public BaseResponse<List<WithdrawResp>> list(
            @RequestParam(required = false) Integer status,
            Authentication auth
    ){
        List<Withdraw> entities = repo.findAllByStatus(status);

        List<WithdrawResp> resp = entities.stream().map(row ->
                WithdrawResp.builder()
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
        return BaseResponse.<List<WithdrawResp>>builder()
                .data(resp)
                .build();
    }

}
