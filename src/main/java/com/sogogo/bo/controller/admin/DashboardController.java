package com.sogogo.bo.controller.admin;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.model.TransactionStatus;
import com.sogogo.bo.repo.DepositRepo;
import com.sogogo.bo.repo.WithdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/dashboard")
public class DashboardController {

    @Autowired
    private DepositRepo depositRepo;
    @Autowired
    private WithdrawRepo withdrawRepo;

    @GetMapping
    public BaseResponse<Map<String,Object>> data(){
        Long dpPending = depositRepo.countAllByStatus(TransactionStatus.PENDING);
        Long wdPending = withdrawRepo.countAllByStatus(TransactionStatus.PENDING);
        return BaseResponse.<Map<String, Object>>builder()
                .error(0)
                .data(Map.of(
                        "dp", dpPending,
                        "wd", wdPending
                ))
                .build();
    }
}
