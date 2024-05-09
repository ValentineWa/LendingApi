package com.yourcompany.database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
public class Repayment {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private BigDecimal amountRepaid;
    private BigDecimal amountOutstanding;
    private Instant repaymentDate;
}