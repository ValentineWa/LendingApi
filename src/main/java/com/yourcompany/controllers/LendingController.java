package com.yourcompany.controllers;

import com.yourcompany.database.Repayment;
import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.LoanRequestDto;
import com.yourcompany.database.Loan;
import com.yourcompany.dto.RepaymentRequestDto;
import com.yourcompany.dto.SubscriberRequestDto;
import com.yourcompany.services.LoanService;
import com.yourcompany.services.RepaymentService;
import com.yourcompany.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/lending")
public class LendingController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private RepaymentService repaymentService;




    @PostMapping("/create-subscriber")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody SubscriberRequestDto subscriberRequest){
        try{
            Subscriber subscriber = subscriberService.createSubscriber(subscriberRequest);
            return ResponseEntity.ok(subscriber);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/loan-request")
    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequestDto request) {
    // implement loan request logic
        try{
            Loan loan = loanService.requestLoan(request);
            return ResponseEntity.ok(loan);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/repayment-request")
    public ResponseEntity<Repayment> repayLoan (@RequestBody RepaymentRequestDto repaymentRequest){

        try{
            Repayment repay = repaymentService.repayLoan(repaymentRequest);
            return ResponseEntity.ok(repay);
        } catch(IllegalArgumentException e){
            return  ResponseEntity.badRequest().body(null);
        }
    }





}


