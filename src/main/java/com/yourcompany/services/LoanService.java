package com.yourcompany.services;

import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.LoanRequestDto;
import com.yourcompany.database.Loan;
import com.yourcompany.dto.SMSRequestDto;
import com.yourcompany.repositories.LoanRepository;
import com.yourcompany.repositories.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class LoanService {
    @Value("${loan.multiple-lending-allowed}")
    private boolean multipleLendingAllowed;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    SMSService smsService;

    @Autowired
    private SubscriberRepository subscriberRepository;
    // ConcurrentHashMap to store maxLoanable values for each subscriber
    private final ConcurrentHashMap<String, BigDecimal> maxLoanableCache = new ConcurrentHashMap<>();

    public Loan requestLoan(LoanRequestDto request) {
        SMSRequestDto smsRequestDto = new SMSRequestDto();

    // implement loan request logic

        if(request.getMsisdn() == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid loan request");
        }

        Subscriber subscriber = subscriberRepository .findSubscriberProfileByMsisdn(request.getMsisdn());

        log.info("the value of sub is {}", subscriber);
        if (subscriber == null) {
            throw new EntityNotFoundException("Subscriber not found with msisdn: " + request.getMsisdn());
        }
        BigDecimal maxLoanable = subscriber.getMaxLoanable();

        //Check if multilending
        if(!multipleLendingAllowed){
            List<Loan> existingLoans = loanRepository.findLoansBySubscriber(request.getMsisdn());
            if(!existingLoans.isEmpty()){
                smsRequestDto.setPhoneNumber(subscriber.getMsisdn());
                smsRequestDto.setMessage("Multiple lending is not allowed. You already has an existing loan.");
                smsService.sendSms(smsRequestDto);
                throw new IllegalArgumentException("Multiple lending is not allowed. Subscriber already has an existing loan.");

            }
        }

        if(maxLoanable.compareTo(BigDecimal.ZERO) == 0){
            smsRequestDto.setPhoneNumber(subscriber.getMsisdn());
            smsRequestDto.setMessage("Ypu are nt eligible to take out a loan at this moment");
            smsService.sendSms(smsRequestDto);
            throw new IllegalArgumentException("Subscriber has no loanable amount: " + maxLoanable);
        }
        //Check if amount is greater than max-loanable
        else if(request.getAmount().compareTo(maxLoanable) > 0){
            smsRequestDto.setPhoneNumber(subscriber.getMsisdn());
            smsRequestDto.setMessage("Ypu are nt eligible to take out a loan at this moment");
            smsService.sendSms(smsRequestDto);
            throw new IllegalArgumentException("Subscriber has exceeded loan amount available to them: " + maxLoanable);
        }

        //Create a new loan record in the db
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setSubscriber(subscriber);
        loan.setAmount(request.getAmount());
        loan.setCreationDate(Instant.now());
        loan.setLoanBalance(request.getAmount());
        loan.setAmountRepaid(BigDecimal.ZERO);
        loan.setDueDate(Instant.now().plus(7, ChronoUnit.DAYS));
        loan.setTransactionStatus(Loan.TransactionStatus.SUCCESS);
        loan.setRepaymentStatus(Loan.RepaymentStatus.PENDING);
        loanRepository.save(loan);

        subscriber.setMaxLoanable(maxLoanable.subtract(request.getAmount()));
        subscriberRepository.save(subscriber);
        smsRequestDto.setPhoneNumber(subscriber.getMsisdn());
        smsRequestDto.setMessage("Your loan request has been disbursed succesffully");
        smsService.sendSms(smsRequestDto);
        return loan;
    }
}