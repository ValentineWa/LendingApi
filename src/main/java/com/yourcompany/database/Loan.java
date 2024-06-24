package com.yourcompany.database;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Getter
@Setter

@Table(name = "loan")
@ToString
@MappedSuperclass
@Inheritance(strategy =  InheritanceType.JOINED)
public class Loan implements Serializable {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "msisdn", referencedColumnName = "msisdn", nullable=false)
    private Subscriber subscriber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private Instant dueDate;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    private BigDecimal amountRepaid;

    private BigDecimal loanBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentStatus repaymentStatus;

    public Loan(
            @NotNull UUID id,
            @NotNull Subscriber subscriber,
            BigDecimal amount,
            Instant creationDate,
            Instant dueDate,
            TransactionStatus transactionStatus,
            BigDecimal amountRepaid,
            BigDecimal loanBalance,
            RepaymentStatus repaymentStatus
            ) {
        this.id = id;
        this.subscriber = subscriber;
        this.amount = amount;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.transactionStatus = transactionStatus;
        this.amountRepaid = amountRepaid;
        this.loanBalance = loanBalance;
        this.repaymentStatus = repaymentStatus;
    }

    public Loan() {

    }


    public enum TransactionStatus {
        PENDING,
        IN_PROGRESS,
        UNRECONCILED,
        FAILED,
        CANCELLED,
        SUCCESS
    }

    public enum RepaymentStatus {
        PENDING,
        PARTIAL,
        FULL
    }
}