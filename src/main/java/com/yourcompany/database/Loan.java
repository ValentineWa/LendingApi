package com.yourcompany.database;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Entity
public class Loan {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "msisdn")
    private Subscriber msisdn;
    private BigDecimal amount;
    private Instant creationDate;
    private Instant dueDate;
    private TransactionStatus transactionStatus;

    public Loan(
            @NotNull UUID id,
            @NotNull Subscriber msisdn,
            BigDecimal amount,
            Instant creationDate,
            Instant dueDate,
            TransactionStatus transactionStatus) {
        this.id = id;
        this.msisdn = msisdn;
        this.amount = amount;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.transactionStatus = transactionStatus;
    }

    public Loan() { }

    public enum TransactionStatus {
        PENDING,
        IN_PROGRESS,
        UNRECONCILED,
        FAILED,
        CANCELLED,
        SUCCESS
    }
}