package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.LoginDto;
import com.sogogo.bo.dto.RegisterDto;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public BaseResponse<String> register(@RequestBody RegisterDto reg){
        int error = 0;
        String msg = "Success";
        try{
            if(userRepository.existsByUsername(reg.getUsername()))
                throw new Exception("Username already exists");
            UserSogogo user = new UserSogogo();
            user.setUsername(reg.getUsername());
            user.setPassword(passwordEncoder.encode( reg.getPassword()));
            userRepository.save(user);
        }catch (Exception e){
            error = -1;
            msg = e.getMessage();
        }

        return BaseResponse.<String>builder()
                .error(error)
                .msg(msg)
                .build();
    }
}
