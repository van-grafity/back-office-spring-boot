package com.sogogo.bo.controller.admin;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.DownlineDto;
import com.sogogo.bo.model.UserRole;
import com.sogogo.bo.model.UserSogogo;
import com.sogogo.bo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sogogo.bo.utils.GlobalUtils.validateUsername;

@RestController
@RequestMapping("api/v1/downline")
public class DownlineController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "create")
    public BaseResponse<String> create(
            @RequestBody DownlineDto reg,
            Authentication auth,
            HttpServletRequest request
            ){
        int status = 0;
        String msg = "";
        String data = "";

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
            user.setParent(reg.getParentId());
            userRepository.save(user);
        }catch (Exception e){
            status = -1;
            msg = e.getMessage();
        }
        return BaseResponse.<String>builder()
                .error(status)
                .data(data)
                .msg(msg)
                .build();
    }
    @GetMapping(value = "list")
    public BaseResponse<List<UserSogogo>> agentList(Authentication auth){
        int status = 0;
        String msg = "";
        List<UserSogogo> list = new ArrayList<>();

        try{
            UserSogogo login = userRepository.findByUsername(auth.getName()).orElseThrow();
            Long parentId = login.getId();
            list = userRepository.findAllByParent(parentId);
        }catch (Exception e){
            status = -1;
            msg = e.getMessage();
        }
        return BaseResponse.<List<UserSogogo>>builder()
                .error(status)
                .data(list)
                .msg(msg)
                .build();
    }
}
