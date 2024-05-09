package com.yourcompany.database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Subscriber {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;
    private String msisdn;
    private BigDecimal maxLoanable;

    public Subscriber(String msisdn){
        this.msisdn = msisdn;
    }

    public Subscriber() {

    }
}