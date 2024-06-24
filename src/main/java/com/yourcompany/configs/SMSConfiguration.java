package com.yourcompany.configs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@Data
public class SMSConfiguration {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
