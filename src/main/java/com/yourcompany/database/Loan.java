package com.yourcompany.database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "msisdn")
    private Subscriber msisdn;
    private BigDecimal amount;
    private Date creationDate;
    private Date dueDate;
    private Enum status;
    // getters and setters
}