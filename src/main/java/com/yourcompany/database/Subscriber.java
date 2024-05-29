package com.yourcompany.database;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subscriber")
@ToString
public class Subscriber implements Serializable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String msisdn;

    @Column(nullable = false)
    private BigDecimal maxLoanable;

    public Subscriber(
            @NotNull UUID id,
            @NotNull String msisdn,
            BigDecimal maxLoanable){
        this.id = id;
        this.msisdn = msisdn;
        this.maxLoanable = maxLoanable;
    }

    public Subscriber() {

    }
}