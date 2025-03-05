package org.example.usermanagementsystem.service;
import org.example.usermanagementsystem.dto.Request.UserRequest;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;
import org.example.usermanagementsystem.dto.Request.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse deposit(Long id, Double amount);
    List<TransactionResponse> getUserTransactions(Long userId);
}
