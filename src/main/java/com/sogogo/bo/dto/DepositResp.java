package com.sogogo.bo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositResp {
    public Long id;
    public Long userId;
    public String username;
    public Long amount;
    public Long paymentMethod;
    public String paymentMethodName;
    public String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date dateAdd;
    public int status;
}
