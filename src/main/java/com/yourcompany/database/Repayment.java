package com.yourcompany.database;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "repayment")
@ToString
public class Repayment implements Serializable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loanId;

    @Column(nullable = false)
    private BigDecimal amountRepaid;

    @Column(nullable = false)
    private BigDecimal amountOutstanding;

    @Column(nullable = false)
    private BigDecimal availableCreditLimit;

    @Column(nullable = false)
    private Instant repaymentDate;



    public Repayment(
            @NotNull UUID id,
            @NotNull Loan loanId,
            BigDecimal amountRepaid,
            BigDecimal amountOutstanding,
            BigDecimal availableCreditLimit,
            Instant repaymentDate
    ){
        this.id = id;
        this.loanId = loanId;
        this.amountRepaid = amountRepaid;
        this.amountOutstanding = amountOutstanding;
        this.availableCreditLimit = availableCreditLimit;
        this.repaymentDate = repaymentDate;
    }
    public Repayment(){}
}