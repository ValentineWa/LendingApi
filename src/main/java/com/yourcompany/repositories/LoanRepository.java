package com.yourcompany.repositories;


import com.yourcompany.database.Loan;
import com.yourcompany.database.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Define interfaces extending JpaRepository or CrudRepository provided by Spring Data JPA.
//These interfaces provide CRUD operations out-of-the-box.
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {


}
