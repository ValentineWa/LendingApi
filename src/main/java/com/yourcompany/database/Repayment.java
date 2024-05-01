package com.yourcompany.database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private BigDecimal amountRepaid;
    private BigDecimal amountOutstanding;
    private Date repaymentDate;
    // getters and setters
}