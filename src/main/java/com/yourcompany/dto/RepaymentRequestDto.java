package com.yourcompany.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class RepaymentRequestDto {
    private String msisdn;
    private BigDecimal amountRepaid;

    public RepaymentRequestDto(String msisdn, BigDecimal amountRepaid ){
        this.msisdn = msisdn;
        this.amountRepaid = amountRepaid;
    }

    public RepaymentRequestDto() {
    }
}
