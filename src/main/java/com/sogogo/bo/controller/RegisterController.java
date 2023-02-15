package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.RegisterDto;
import com.sogogo.bo.model.UserRole;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

import static com.sogogo.bo.utils.GlobalUtils.validateUsername;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public BaseResponse<String> register(@RequestBody RegisterDto reg, HttpServletRequest request){
        int error = 0;
        String msg = "Success";
        try{
            String username = reg.getUsername();
            if(!validateUsername(username)) throw new Exception("Invalid username");
            if(userRepository.existsByUsername(username)) throw new Exception("Username already exists");
            UserSogogo user = new UserSogogo();
            user.setUsername(username);
            user.setName(reg.getFullName());
            user.setPassword(passwordEncoder.encode( reg.getPassword()));
            user.setRole(UserRole.PLAYER);
            user.setEnabled(1);
            user.setDateAdd(Date.from(Instant.now()));
            user.setIp(request.getRemoteAddr());
            user.setReferral(reg.getReferral());
            //todo: setParent which is agentId
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
