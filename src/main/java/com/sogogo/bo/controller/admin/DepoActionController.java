package com.sogogo.bo.controller.admin;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.AdminDpAction;
import com.sogogo.bo.model.Deposit;
import com.sogogo.bo.model.TransactionStatus;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.DepositRepo;
import com.sogogo.bo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("admin/deposit/action")
public class DepoActionController {
    @Autowired
    private DepositRepo depositRepo;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public BaseResponse<Boolean> approve(@RequestBody AdminDpAction dto, Authentication auth){
        boolean resp = true;
        int status = 0;
        String msg = "Success";
        try{
            Deposit deposit = depositRepo.findByIdAndStatus(dto.getId(), TransactionStatus.PENDING)
                    .orElseThrow(()->new Exception("Transaction not found"));
            UserSogogo admin = userRepository.findByUsername(auth.getName()).orElseThrow();
            UserSogogo userWd = deposit.getUser();
            BigDecimal userBalance = userWd.getBalance();

            int dtoStatus = dto.getStatus();
            Date now = Date.from(Instant.now());
            deposit.setStatus(dtoStatus);
            deposit.setDescription(dto.getNote());
            deposit.setUpdateBy(admin);
            if(TransactionStatus.APPROVE.equals(dtoStatus)){
                deposit.setDateApprove(now);
            }
            if(TransactionStatus.REJECT.equals(dtoStatus)){
                deposit.setDateReject(now);
            }
            depositRepo.save(deposit);

            //update user balance
            BigDecimal newBalance = userBalance.add(BigDecimal.valueOf(deposit.getAmount()));
            userWd.setBalance(newBalance);
            userRepository.save(userWd);
        }catch (Exception e){
            resp = false;
            status = -1;
            msg = e.getMessage();
            log.error("Error: ", e);
        }
        return BaseResponse.<Boolean>builder()
                .error(status)
                .data(resp)
                .msg(msg)
                .build();
    }
}
