package com.yourcompany.configs;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {
    private final SMSConfiguration smsConfiguration;

    @Autowired
    public TwilioInitializer(SMSConfiguration smsConfiguration) {
        this.smsConfiguration = smsConfiguration;
        Twilio.init(smsConfiguration.getAccountSid(), smsConfiguration.getAuthToken());
    }
}
