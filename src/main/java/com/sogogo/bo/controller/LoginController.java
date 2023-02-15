package com.sogogo.bo.controller;

import com.sogogo.bo.dto.BaseResponse;
import com.sogogo.bo.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping
    public BaseResponse<String> getToken(@RequestBody LoginDto login)throws AuthenticationException {
        int error = 0;
        String msg = "Login success";
        String token = "";
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            User user = (User) authentication.getPrincipal();
            Instant now = Instant.now();
            long expiry = 36000L;

            String scope =
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(joining(" "));

            JwtClaimsSet claims =
                    JwtClaimsSet.builder()
                            .issuer("sogogo-engine")
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(expiry))
                            .subject(format("%s", user.getUsername()))
                            .claim("roles", scope)
                            .build();

            token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        }catch (Exception e){
            error = -1;
            msg = e.getMessage();
        }

        return BaseResponse.<String>builder()
                .error(error)
                .msg(msg)
                .data(token)
                .build();
    }
}
