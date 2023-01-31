package com.sogogo.bo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @Length(min = 6, max = 32, message = "PASSWORD_IS_REQUIRED")
    private String currentPassword;
    @Length(min = 6, max = 32, message = "NEW_PASSWORD_IS_REQUIRED")
    private String newPassword;
    @Length(min = 6, max = 32, message = "CONFIRM_PASSWORD_IS_REQUIRED")
    private String confirmNewPassword;
}
