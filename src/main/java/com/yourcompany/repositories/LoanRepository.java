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

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT s FROM Loan s WHERE s.subscriber.msisdn = :msisdn ORDER BY s.creationDate ASC")
    List<Loan> findLoansBySubscriber(@Param("msisdn") String msisdn);


    @Query("SELECT s FROM Loan s WHERE s.subscriber.msisdn = :msisdn")
    Loan findSingleLoanBySubscriber(@Param("msisdn") String msisdn);

    @Query("SELECT s FROM Loan s WHERE s.dueDate < CURRENT_DATE ORDER BY s.dueDate ASC")
    List<Loan> findOverDueLoans();

    @Query("SELECT s FROM Loan s")
    List<Loan> findLoans();

}