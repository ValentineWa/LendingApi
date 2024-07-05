package com.yourcompany.services;

import com.yourcompany.database.DefaultedLoans;
import com.yourcompany.database.Loan;
import com.yourcompany.repositories.DefaultedLoansRepository;
import com.yourcompany.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Service
@Slf4j
public class SweepDefaultedLoans {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private DefaultedLoansRepository defaultedLoansRepository;



    @Value("${default.loan.months}")
    private int monthsToDefault;
    @Scheduled(cron = "${proc.scheduler}")
    public void defaultedLoans(){
        //List all loans

        DefaultedLoans defaulted = null;

        List<Loan> allDefaultedLoans = loanRepository.findOverDueLoans();

        if(allDefaultedLoans !=null && !allDefaultedLoans.isEmpty()){

        //Check if they are open or not
        for(Loan defaults : allDefaultedLoans){
            Loan.RepaymentStatus stats = defaults.getRepaymentStatus();
            if(stats != Loan.RepaymentStatus.FULL){
                log.info("Sweep has started ........");
                //Check if the due date has exceeded x number of months
                LocalDate dueDate = defaults.getDueDate().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period loanTime = Period.between(dueDate, today);
                int duration = loanTime.getMonths();
                if(duration >= monthsToDefault){
                    defaulted = new DefaultedLoans();
                    defaulted.setDefaultedDate(Instant.now());
                    defaulted.setDefaultedAmount(defaults.getLoanBalance());
                    defaulted.setLoanAmount(defaults.getAmount());
                    defaulted.setLoanDate(defaults.getCreationDate());
                    defaulted.setDueDate(defaults.getDueDate());
                    defaulted.setLoanId(defaults);
                    defaultedLoansRepository.save(defaulted);
                    log.info("Sweep completed successfully....");
                }
            }
        }
        } else {
            throw new IllegalArgumentException("No loan found for sweeping.");
        }
    }
}