package com.yourcompany.services;

import com.sun.istack.NotNull;
import com.yourcompany.database.Loan;
import com.yourcompany.database.Repayment;
import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.RepaymentRequestDto;
import com.yourcompany.repositories.LoanRepository;
import com.yourcompany.repositories.RepaymentRepository;
import com.yourcompany.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
public class RepaymentService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    public Repayment repayLoan(RepaymentRequestDto repaymentRequest){

        if(repaymentRequest.getMsisdn() == null || repaymentRequest.getAmountRepaid() == null || repaymentRequest.getAmountRepaid().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid repayment request");
        }
        //Check if amount repaid is greater than loan amount

        //Check if msisdn exists
        Subscriber subscriber = subscriberRepository .findSubscriberProfileByMsisdn(repaymentRequest.getMsisdn());
        if (subscriber == null) {
            throw new EntityNotFoundException("Subscriber not found with msisdn: " + repaymentRequest.getMsisdn());
        }

        //Check if msisdn has a loan
         List <Loan> allSubLoans = loanRepository.findLoansBySubscriber(repaymentRequest.getMsisdn());

        Repayment repay = null;

        if(allSubLoans != null && !allSubLoans.isEmpty()){
            for(Loan loan : allSubLoans){


               if(repaymentRequest.getAmountRepaid().compareTo(loan.getAmount()) <= 0){
                   BigDecimal amount = loan.getAmount().subtract(repaymentRequest.getAmountRepaid());



                   loan.setAmountRepaid(repaymentRequest.getAmountRepaid());
                   loan.setLoanBalance(amount);

                   if(amount.equals(BigDecimal.ZERO)){
                       loan.setRepaymentStatus(Loan.RepaymentStatus.FULL);
                   } else {
                       loan.setRepaymentStatus(Loan.RepaymentStatus.PARTIAL);
                   }
                   loanRepository.save(loan);

                    repay = new Repayment();
                   repay.setId(UUID.randomUUID());
                   repay.setLoanId(loan);
                   repay.setAmountRepaid(repaymentRequest.getAmountRepaid());
                   repay.setAmountOutstanding(amount);
                   repay.setAvailableCreditLimit(subscriber.getMaxLoanable().subtract(loan.getAmount()));
                   repay.setRepaymentDate(Instant.now());
                   repaymentRepository.save(repay);
               } else {
                   throw new IllegalArgumentException("Repayment amount exceeds loan amount");
               }
        }
      }else {
            throw new EntityNotFoundException("Loan not found for msisdn: " + repaymentRequest.getMsisdn());
        }
        return repay;
    }
}