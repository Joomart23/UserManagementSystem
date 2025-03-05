package org.example.usermanagementsystem.service;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse transfer(Long fromUserId, Long toUserId, Double amount);
}
