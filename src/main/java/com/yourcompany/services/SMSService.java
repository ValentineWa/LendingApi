package com.yourcompany.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.yourcompany.configs.SMSConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Configuration
@Slf4j
public class SMSService {
    private final SMSConfiguration smsConfiguration;
    private final static Logger LOGGER = LoggerFactory.getLogger(SMSService.class);
    @Autowired
    public SMSService(SMSConfiguration smsConfiguration){
//        try{
//            Twilio.init(
//                    smsConfiguration.getAccountSid(),
//                    smsConfiguration.getAuthToken()
//            );
//            Message.creator(
//                    new PhoneNumber("+254705325040"),
//                    new PhoneNumber(smsConfiguration.getTrialNumber()),"Hi from Twilio").create();
//
//            return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
//            log.info("Message has been sent successfully");
//
//
//        } catch (Exception e){
//            log.info("Message not sent");
//        }
        this.smsConfiguration = smsConfiguration;
        Twilio.init(
                   smsConfiguration.getAccountSid(),
                   smsConfiguration.getAuthToken()
            );
        LOGGER.info("aTwilio initialized... with account sid {}", smsConfiguration);


    }


}
