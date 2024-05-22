package com.yourcompany.dto;

import com.yourcompany.database.Subscriber;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanRequestDto {
    private String msisdn;
    private BigDecimal amount;

    public LoanRequestDto(String msisdn, BigDecimal amount) {
        this.msisdn = msisdn;
        this.amount = amount;
    }
    public LoanRequestDto(){}

}
