package com.sogogo.bo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawDto {
    @Min(1) @Max(100_000)
    private Long amount;
    @NotNull
    private Long paymentMethod;
    private String description;
}
