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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
@RequestMapping("/lending")
public class LendingController {
    @Value("${loan.multiple-lending-allowed}")
    private boolean multipleLendingAllowed;

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
            log.info("Subscriber with msisdn {} has been created successfully", subscriberRequest.getMsisdn());
            return ResponseEntity.ok(subscriber);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/loan-request")
    public ResponseEntity<Object> requestLoan(@RequestBody LoanRequestDto request) {
    // implement loan request logic
        try{
            Loan loan = loanService.requestLoan(request);
            log.info("Loan of amount {} for msisdn {} has been created successfully", request.getAmount(),request.getMsisdn());
            return ResponseEntity.ok(loan);
        } catch(IllegalArgumentException e){
            if (e.getMessage().contains("Invalid loan request.")) {
                return ResponseEntity.badRequest().body("Invalid loan request.");
            } else if (e.getMessage().contains("Loan not found")) {
                return ResponseEntity.notFound().build();
            }  else if (e.getMessage().contains("Subscriber not found with msisdn")) {
                return ResponseEntity.badRequest().body("Subscriber not found with the msisdn");
            }   else if (e.getMessage().contains("Multiple lending is not allowed")) {
                return ResponseEntity.badRequest().body("Multiple lending is not allowed. Subscriber already has an existing loan");
            }   else if (e.getMessage().contains("Subscriber has no loanable amount")) {
                return ResponseEntity.badRequest().body("Subscriber has no loanable amount");
            }   else if (e.getMessage().contains("Subscriber has exceeded loan amount available to them")) {
                return ResponseEntity.badRequest().body("Subscriber has exceeded loan amount available to them");
            }  else {
                return ResponseEntity.badRequest().body("An error occurred. Please try again.");
            }
        }
    }


    @PostMapping("/repayment-request")
    public ResponseEntity<Object> repayLoan (@RequestBody RepaymentRequestDto repaymentRequest){

        try{
            Repayment repay = multipleLendingAllowed ? repaymentService.repayMultipleLoans(repaymentRequest) : repaymentService.repaySingleLoan(repaymentRequest);
            return ResponseEntity.ok(repay);
        } catch(IllegalArgumentException e){
            if (e.getMessage().contains("Invalid repayment amount")) {
                return ResponseEntity.badRequest().body("Invalid repayment amount. Please enter a valid amount.");
            } else if (e.getMessage().contains("Loan not found")) {
                return ResponseEntity.notFound().build();
            }  else if (e.getMessage().contains("Repayment amount exceeds")) {
                return ResponseEntity.badRequest().body("Repayment amount exceeds loan amount");
            } else {
                return ResponseEntity.badRequest().body("An error occurred. Please try again.");
            }
        }
    }

}


