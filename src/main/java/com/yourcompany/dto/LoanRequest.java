package com.yourcompany.dto;

import com.yourcompany.database.Subscriber;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanRequest {
    private Subscriber msisdn;
    private BigDecimal amount;

    public LoanRequest(Subscriber msisdn, BigDecimal amount) {
        this.msisdn = msisdn;
        this.amount = amount;
    }
    public LoanRequest(){}

}
