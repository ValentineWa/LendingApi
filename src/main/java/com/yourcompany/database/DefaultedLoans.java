package com.yourcompany.database;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tbl_defaulted_loans")
@ToString

public class DefaultedLoans implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Instant defaultedDate;

    @Column(nullable = false)
    private BigDecimal defaultedAmount;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan loanId;


    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private Instant loanDate;

    @Column(nullable = false)
    private Instant dueDate;

    public DefaultedLoans(
            @NotNull UUID id,
            Instant defaultedDate,
            BigDecimal defaultedAmount){
        this.id = id;
        this.defaultedDate = defaultedDate;
        this.defaultedAmount = defaultedAmount;
    }
    public DefaultedLoans(){
        super();
    }

}