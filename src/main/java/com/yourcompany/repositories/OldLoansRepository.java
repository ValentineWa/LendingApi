package com.yourcompany.repositories;

import com.yourcompany.database.Loan;
import com.yourcompany.database.OldLoans;
import com.yourcompany.database.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OldLoansRepository extends JpaRepository<OldLoans, Long> {
}
