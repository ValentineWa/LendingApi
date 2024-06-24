package com.yourcompany.controllers;

import com.yourcompany.services.SMSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SMSController {
    private SMSService smsService;

    public SMSController(SMSService smsService){
        this.smsService = smsService;
    }

//    @GetMapping(value = "/sendSMS")
//    public ResponseEntity<String> sendSMS(){
//        return smsService.sendSms();
//    }

}