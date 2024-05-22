package com.yourcompany.services;

import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.LoanRequestDto;
import com.yourcompany.database.Loan;
import com.yourcompany.repositories.LoanRepository;
import com.yourcompany.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Component
@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;
    public Loan requestLoan(LoanRequestDto request) {
    // implement loan request logic

        if(request.getMsisdn() == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid loan request");
        }

        Subscriber subscriber = subscriberRepository .findSubscriberProfileByMsisdn(request.getMsisdn());
        if (subscriber == null) {
            throw new EntityNotFoundException("Subscriber not found with msisdn: " + request.getMsisdn());
        }

        //Create a new loan record in the db
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setSubscriber(subscriber);
        loan.setAmount(request.getAmount());
        loan.setCreationDate(Instant.now());
        loan.setDueDate(Instant.now().plus(7, ChronoUnit.DAYS));
        loan.setTransactionStatus(Loan.TransactionStatus.SUCCESS);
        loanRepository.save(loan);
        return loan;
    }
}