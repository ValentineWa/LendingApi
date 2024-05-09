package com.yourcompany.dto;

import java.math.BigDecimal;

public class RepaymentRequest {
    private Long loanId;
    private BigDecimal amountRepaid;
    public RepaymentRequest(Long loanId, BigDecimal amountRepaid ){
        this.loanId = loanId;
        this.amountRepaid = amountRepaid;
    }

    public BigDecimal getAmountRepaid(){
        return amountRepaid;
    }

    public void setAmountRepaid(BigDecimal amountRepaid){
        this.amountRepaid = amountRepaid;
    }

    public Long getLoanId(){
        return loanId;
    }

    public void setLoanId(Long loanId){
        this.loanId = loanId;
    }



}
