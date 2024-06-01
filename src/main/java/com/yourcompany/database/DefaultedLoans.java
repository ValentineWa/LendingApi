package com.yourcompany.database;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_defaulted_loans")
@ToString
@Inheritance(strategy =  InheritanceType.JOINED)
public class DefaultedLoans extends Loan{

    @Column(nullable = false)
    private Instant defaultedDate;

    @Column(nullable = false)
    private BigDecimal defaultedAmount;




    public DefaultedLoans(Instant defaultedDate, BigDecimal defaultedAmount){
        this.defaultedDate = defaultedDate;
        this.defaultedAmount = defaultedAmount;
    }
    public DefaultedLoans(){
        super();
    }

}