package com.yourcompany.services;

import com.yourcompany.database.Subscriber;
import com.yourcompany.dto.SubscriberRequestDto;
import com.yourcompany.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.UUID;


@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber createSubscriber(SubscriberRequestDto subscriberRequest){

        if(subscriberRequest.getMsisdn() == null || subscriberRequest.getMaxLoanable() == null || subscriberRequest.getMaxLoanable().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid Subscriber request");
        }

        Subscriber subscriber = subscriberRepository .findSubscriberProfileByMsisdn(subscriberRequest.getMsisdn());
        if (subscriber != null) {
            throw new EntityNotFoundException("Subscriber already exists: " + subscriberRequest.getMsisdn());
        }
               Subscriber sub = new Subscriber();
                sub.setId(UUID.randomUUID());
                sub.setMsisdn(String.valueOf(subscriberRequest.getMsisdn()));
                sub.setMaxLoanable(subscriberRequest.getMaxLoanable());
                subscriberRepository.save(sub);
                return sub;
    }
}