package com.yourcompany.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.yourcompany.configs.SMSConfiguration;
import com.yourcompany.dto.SMSRequestDto;
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
    @Autowired
    SMSConfiguration smsConfiguration;
  public void sendSms(SMSRequestDto smsRequestDto) {
     try{

                PhoneNumber to = new PhoneNumber(smsRequestDto.getPhoneNumber());
                PhoneNumber from = new PhoneNumber(smsConfiguration.getTrialNumber());
                String message = smsRequestDto.getMessage();
                MessageCreator creator = Message.creator(to, from, message);

                creator.create();

                log.info("SMS sent successfully");
            }catch(Exception e){
                log.error("SMS could not be sent due to: {}", e.getMessage());
            }
        }

}
