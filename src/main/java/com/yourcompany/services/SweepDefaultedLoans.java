//package com.yourcompany.services;
//
//import com.yourcompany.database.DefaultedLoans;
//import com.yourcompany.database.Loan;
//import com.yourcompany.repositories.DefaultedLoansRepository;
//import com.yourcompany.repositories.LoanRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.Period;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//
//@EnableScheduling
//@Service
//public class SweepDefaultedLoans {
//
//    private LoanRepository loanRepository;
//    private DefaultedLoansRepository defaultedLoansRepository;
//
//
//
//    @Value("${default.loan.months}")
//    private int monthsToDefault;
//    @Scheduled(cron = "${proc.scheduler}")
//    public void defaultedLoans(){
//        //List all loans
//
//        DefaultedLoans defaulted = null;
//
//        List<Loan> allDefaultedLoans = loanRepository.findAll();
//
//        if(allDefaultedLoans !=null && !allDefaultedLoans.isEmpty()){
//
//        //Check if they are open or not
//        for(Loan defaults : allDefaultedLoans){
//            Loan.RepaymentStatus stats = defaults.getRepaymentStatus();
//            if(stats != Loan.RepaymentStatus.FULL){
//                //Check if the due date has exceeded 6 months
//                LocalDate dueDate =defaults.getDueDate().atZone(ZoneId.systemDefault()).toLocalDate();
//                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                Period loanTime = Period.between(today, dueDate);
//                int duration = loanTime.getMonths();
//                if(duration >= monthsToDefault){
//                    defaulted = new DefaultedLoans();
//                    defaulted.setDefaultedDate(Instant.now());
//                    defaulted.setDefaultedAmount(defaults.getLoanBalance());
//                    defaultedLoansRepository.save(defaulted);
////                    loanRepository.delete(defaulted);
//                }
//            }else {
//                throw new IllegalArgumentException("No loans found for sweeping.");
//            }
//        }
//        }
//    }
//}