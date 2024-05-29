package com.yourcompany.repositories;

import com.yourcompany.database.Loan;
import com.yourcompany.database.Repayment;
import com.yourcompany.database.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

}