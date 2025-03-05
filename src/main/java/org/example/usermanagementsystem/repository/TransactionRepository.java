package org.example.usermanagementsystem.repository;

import org.example.usermanagementsystem.entity.Transaction;
import org.example.usermanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.fromUser = :user OR t.toUser = :user")
    List<Transaction> findByFromUserOrToUser(@Param("user") User user);

}
