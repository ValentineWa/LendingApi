package com.yourcompany.controllers;

import com.yourcompany.dto.LoanRequest;
import com.yourcompany.dto.RepaymentRequest;
import com.yourcompany.database.Loan;
import com.yourcompany.database.Repayment;
import com.yourcompany.database.Subscriber;
import com.yourcompany.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


//Create RESTful controllers to handle incoming HTTP requests. Annotate these classes with @RestController.
//Define endpoints and map them to appropriate methods. Use annotations like @PostMapping, @GetMapping, etc.
//Inject service classes into controllers to delegate business logic.


@RestController
@RequestMapping("/lending")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/loan-request")
//    Controller Layer:
//    Receives HTTP requests from clients and delegates business logic to the service layer.
//    Responsible for handling HTTP-specific concerns such as request validation, generating appropriate HTTP responses, and mapping request parameters to method arguments.
//    Often handles authentication, authorization, and input validation before passing data to the service layer.


    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequest request) {
    // implement loan request logic

        //Validate the request
        if(request.getMsisdn() == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            return ResponseEntity.badRequest().build();
        }
        //Check if user exists
        boolean userExists = checkIfUserExists(request.getMsisdn());
        if(!userExists){
            return ResponseEntity.notFound().build();
        }

        //Determine if the user is eligible for a loan
        boolean isEligible = checkLoanEligibility(request.getMsisdn());
        if(!isEligible){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        //Create a new loan record in the database
        Loan loan = createLoanRecord(request.getMsisdn(), request.getAmount());
        if(loan != null){
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Loan createLoanRecord(Subscriber msisdn, BigDecimal amount) {
        return new Loan();
    }

    private boolean checkLoanEligibility(Subscriber msisdn) {
        return true;
    }

    private boolean checkIfUserExists(Subscriber msisdn) {
        return msisdn.equals("123456");
    }

    @PostMapping("/repayment-request")
    public ResponseEntity<Repayment> repayLoan(@RequestBody RepaymentRequest request) {
        // implement repayment logic
        return null;
    }
}
