package com.yourcompany.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubscriberRequestDto {

    private String msisdn;
    private BigDecimal maxLoanable;

    public SubscriberRequestDto(String msisdn, BigDecimal maxLoanable){
        this.msisdn = msisdn;
        this.maxLoanable = maxLoanable;
    }

    public SubscriberRequestDto(){}
}
