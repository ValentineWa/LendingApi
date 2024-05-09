package com.yourcompany.repositories;


import com.yourcompany.database.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

//Define interfaces extending JpaRepository or CrudRepository provided by Spring Data JPA.
//These interfaces provide CRUD operations out-of-the-box.

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
