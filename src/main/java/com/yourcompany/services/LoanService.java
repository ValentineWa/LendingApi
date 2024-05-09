package com.yourcompany.services;

import com.yourcompany.dto.LoanRequest;
import com.yourcompany.dto.RepaymentRequest;
import com.yourcompany.database.Loan;
import com.yourcompany.database.Repayment;
import com.yourcompany.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


//Implement business logic in service classes. Annotate these classes with @Service.
//Encapsulate data manipulation and interaction with repositories in service methods.
@Component

public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
//    Service Layer:
//    Contains business logic and orchestrates interactions between different components of the application.
//    Responsible for implementing the core functionality of the application, including complex business rules, data manipulation, and interactions with repositories or external services.
//    Encapsulates reusable and independent units of work, promoting modularity and maintainability.

    public Loan requestLoan(LoanRequest request) {
    // implement loan request logic

        //Create a new loan record in the db
        Loan loan = new Loan();
        loan.setCreationDate(new Date().toInstant());
        loan.setMsisdn(request.getMsisdn());
        loan.setAmount(request.getAmount());
        loan.setDueDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)).toInstant());
        loan.setTransactionStatus(Loan.TransactionStatus.SUCCESS);
        loanRepository.save(loan);
        return loan;
    }

    public Repayment repayLoan(RepaymentRequest request) {
        return null;
        // implement repayment logic
    }



}
