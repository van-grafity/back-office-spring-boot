package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.ChangePasswordDto;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/changePass")
public class ChangePasswordController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public BaseResponse<Boolean> submit(@RequestBody ChangePasswordDto dto, Authentication auth){
        int error = 0;
        String msg = "Password has been changed! Please re-login";
        Boolean resp = true;
        try{
            if(!dto.getNewPassword().equals(dto.getConfirmNewPassword()))
                throw new Exception("Confirm password not match");

            UserSogogo user = userRepository.findByUsername(auth.getName()).orElseThrow();
            String curPassword = user.getPassword();
            if(!passwordEncoder.matches(dto.getCurrentPassword(), curPassword)){
                throw new Exception("Incorrect password");
            }
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
        }catch (Exception e){
            error = -1;
            msg = e.getMessage();
            resp = false;
        }
        return BaseResponse.<Boolean>builder()
                .msg(msg)
                .error(error)
                .data(resp)
                .build();
    }
}
