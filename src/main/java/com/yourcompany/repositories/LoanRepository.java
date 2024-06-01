package com.yourcompany.repositories;


import com.yourcompany.database.Loan;
import com.yourcompany.database.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Define interfaces extending JpaRepository or CrudRepository provided by Spring Data JPA.
//These interfaces provide CRUD operations out-of-the-box.
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT s FROM Loan s WHERE s.subscriber.msisdn = :msisdn")
    List<Loan> findLoansBySubscriber(@Param("msisdn") String msisdn);

}