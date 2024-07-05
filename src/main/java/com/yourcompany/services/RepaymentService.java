package com.yourcompany.services;

import com.sun.istack.NotNull;
import com.yourcompany.database.Loan;
import com.yourcompany.database.Repayment;
import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.RepaymentRequestDto;
import com.yourcompany.dto.SMSRequestDto;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class RepaymentService {
    @Autowired
    SMSService smsService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    // Helper function to sort loans by creation date
    private List<Loan> sortLoansByOldest(List<Loan> loans) {
        Collections.sort(loans, new Comparator<Loan>() {
            public int compare(Loan loan1, Loan loan2) {
                return loan1.getCreationDate().compareTo(loan2.getCreationDate());
            }
        });
        return loans;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private Repayment repayLoan(RepaymentRequestDto repaymentRequest, Loan loan) {
    SMSRequestDto smsRequestDto = new SMSRequestDto();
    if (repaymentRequest.getAmountRepaid().compareTo(loan.getLoanBalance()) <= 0) {
        BigDecimal amount = loan.getLoanBalance().subtract(repaymentRequest.getAmountRepaid());

        loan.setAmountRepaid(repaymentRequest.getAmountRepaid());
        loan.setLoanBalance(amount);

        if (amount.equals(BigDecimal.ZERO)) {
            loan.setRepaymentStatus(Loan.RepaymentStatus.FULL);
            log.info("Loan amount {} for msisdn {} has been repaid fully", repaymentRequest.getAmountRepaid(), repaymentRequest.getMsisdn());
        } else {
            loan.setRepaymentStatus(Loan.RepaymentStatus.PARTIAL);
            log.info("Loan amount {} for msisdn {} has been repaid partially", repaymentRequest.getAmountRepaid(), repaymentRequest.getMsisdn());
        }
        loanRepository.save(loan);

        Repayment repay = new Repayment();
        repay.setId(UUID.randomUUID());
        repay.setLoanId(loan);
        repay.setAmountRepaid(repaymentRequest.getAmountRepaid());
        repay.setAmountOutstanding(loan.getLoanBalance());
        repay.setAvailableCreditLimit(subscriberRepository.findSubscriberProfileByMsisdn(repaymentRequest.getMsisdn()).getMaxLoanable());
        repay.setRepaymentDate(Instant.now());
        repaymentRepository.save(repay);
        smsRequestDto.setPhoneNumber(repaymentRequest.getMsisdn());
        smsRequestDto.setMessage("You have made a repayment successfully.");
        smsService.sendSms(smsRequestDto);
        return repay;
    } else {
        smsRequestDto.setPhoneNumber(repaymentRequest.getMsisdn());
        smsRequestDto.setMessage("Repayment amount exceeds loan amount");
        smsService.sendSms(smsRequestDto);
        throw new IllegalArgumentException("Repayment amount exceeds loan amount.");
    }
}




//SINGLE LOAN//////////////////


    public Repayment repaySingleLoan(RepaymentRequestDto repaymentRequest) {
        Loan loan = loanRepository.findSingleLoanBySubscriber(repaymentRequest.getMsisdn());
        if (loan == null) {
            throw new EntityNotFoundException("Loan not found for msisdn: " + repaymentRequest.getMsisdn());
        }
        return repayLoan(repaymentRequest, loan);
    }




///////////////MULTIPLE LOANS////////////////////////////

    public Repayment repayMultipleLoans(RepaymentRequestDto repaymentRequest) {
        List<Loan> allSubLoans = loanRepository.findLoansBySubscriber(repaymentRequest.getMsisdn());
        if (allSubLoans == null || allSubLoans.isEmpty()) {
            throw new EntityNotFoundException("Loan not found for msisdn: " + repaymentRequest.getMsisdn());
        }

        BigDecimal amountToRepay = repaymentRequest.getAmountRepaid();
        Repayment lastRepayment = null;

        for (Loan loan : allSubLoans) {
            if (amountToRepay.compareTo(BigDecimal.ZERO) <= 0) {
                break; // Stop if the repaid amount runs out
            }

            BigDecimal loanBalance = loan.getLoanBalance();
            BigDecimal amountRepaid;

            if (amountToRepay.compareTo(loanBalance) >= 0) {
                amountRepaid = loanBalance;
            } else {
                amountRepaid = amountToRepay;
            }

            RepaymentRequestDto singleLoanRepaymentRequest = new RepaymentRequestDto();
            singleLoanRepaymentRequest.setMsisdn(repaymentRequest.getMsisdn());
            singleLoanRepaymentRequest.setAmountRepaid(amountRepaid);

            lastRepayment = repayLoan(singleLoanRepaymentRequest, loan);

            amountToRepay = amountToRepay.subtract(amountRepaid);
        }

        return lastRepayment;
    }

}