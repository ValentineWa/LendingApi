package com.yourcompany.services;

import com.yourcompany.database.DefaultedLoans;
import com.yourcompany.database.Loan;
import com.yourcompany.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SweepDefaultedLoans {

    private LoanRepository loanRepository;


    @Value("${default.loan.months")
    private int monthsToDefault;
    public void defaultedLoans(){
        //List all loans

        DefaultedLoans defaulted = null;

        List<Loan> allDefaultedLoans = loanRepository.findAll();

        if(allDefaultedLoans !=null && !allDefaultedLoans.isEmpty()){


        //Check if they are open or not
        for(Loan defaults : allDefaultedLoans){
            Loan.RepaymentStatus stats = defaults.getRepaymentStatus();
            if(stats != Loan.RepaymentStatus.FULL){
                //Check if the due date has exceeded 6 months
                LocalDate dueDate =defaults.getDueDate().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period loanTime = Period.between(today, dueDate);
                int duration = loanTime.getMonths();
                if(duration >= monthsToDefault){
                    defaulted = new DefaultedLoans();
                    defaulted.setDefaultedDate(Instant.now());
                    defaulted.setDefaultedAmount(defaults.getLoanBalance())
                }


            }else {
                throw new IllegalArgumentException("No loans found for sweeping.");
            }

        }
        }

        //If not leave it there
        //If not, add it to table defaulted loans
        //Then delete the record from tbl loans


        //Defauletd willl inherit tbl loans and have other newer columns

    }






}
