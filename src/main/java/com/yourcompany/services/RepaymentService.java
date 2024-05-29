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
        Loan allSubLoans = loanRepository.findLoansBySubscriber(repaymentRequest.getMsisdn());

        //Get Loan ID
//        Loan loan = loanRepository.findLoanIdBySubscriber(repaymentRequest.getMsisdn());

        if(allSubLoans != null){
               if(repaymentRequest.getAmountRepaid().compareTo(allSubLoans.getAmount()) <= 0){
                   BigDecimal amount = allSubLoans.getAmount().subtract(repaymentRequest.getAmountRepaid());
                   allSubLoans.setAmountRepaid(repaymentRequest.getAmountRepaid());
                   allSubLoans.setLoanBalance(amount);

                   if(amount.equals(0)){
                       allSubLoans.setRepaymentStatus(Loan.RepaymentStatus.FULL);
                   } else {
                       allSubLoans.setRepaymentStatus(Loan.RepaymentStatus.PARTIAL);
                   }
                   loanRepository.save(allSubLoans);

                   Repayment repay = new Repayment();
                   repay.setId(UUID.randomUUID());
                   repay.setLoanId(allSubLoans);
                   repay.setAmountRepaid(repaymentRequest.getAmountRepaid());
                   repay.setAmountOutstanding(amount);
                   repay.setAvailableCreditLimit(subscriber.getMaxLoanable().subtract(allSubLoans.getAmount()));
                   repay.setRepaymentDate(Instant.now());
                   repaymentRepository.save(repay);
                  return repay;
               } else {
                   throw new IllegalArgumentException("Repayment amount exceeds loan amount");
               }
        } else{
            throw new EntityNotFoundException("Loan not found for msisdn: " + repaymentRequest.getMsisdn());
        }

    }
}
