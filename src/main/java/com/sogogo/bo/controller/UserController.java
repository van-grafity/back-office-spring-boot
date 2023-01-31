package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("detail")
    public BaseResponse<UserSogogo> detail(Authentication auth){
        UserSogogo user = new UserSogogo();
        int error = 0;
        String msg = "";
        try{
            user = userRepo.findByUsername(auth.getName()).orElseThrow();
        }catch (Exception e){
            error = -1;
            msg = e.getMessage();
        }
        return BaseResponse.<UserSogogo>builder()
                .error(error)
                .msg(msg)
                .data(user)
                .build();
    }
}
