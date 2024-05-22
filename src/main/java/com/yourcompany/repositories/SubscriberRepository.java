package com.yourcompany.repositories;

import com.yourcompany.database.Loan;
import com.yourcompany.database.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, String> {


    @Query("SELECT s FROM Subscriber s WHERE s.msisdn  = :msisdn")
    Subscriber findSubscriberProfileByMsisdn(@Param("msisdn") String msisdn);
}
