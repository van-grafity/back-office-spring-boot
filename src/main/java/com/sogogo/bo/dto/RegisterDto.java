package com.sogogo.bo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Length(min = 6, max = 32, message = "USERNAME_IS_REQUIRED")
    private String username;
    @Length(min = 3, max = 50, message = "FULLNAME_IS_REQUIRED")
    private String fullName;
    @Length(min = 6, max = 32, message = "PASSWORD_IS_REQUIRED")
    private String password;
    private Long referral;

}
