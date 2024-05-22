package com.yourcompany.database;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "loan")
@ToString
public class Loan {
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

    private TransactionStatus transactionStatus;

    public Loan(
            @NotNull UUID id,
            @NotNull Subscriber subscriber,
            BigDecimal amount,
            Instant creationDate,
            Instant dueDate,
            TransactionStatus transactionStatus) {
        this.id = id;
        this.subscriber = subscriber;
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