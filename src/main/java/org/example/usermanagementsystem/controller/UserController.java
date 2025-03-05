package org.example.usermanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.usermanagementsystem.dto.Request.DepositRequest;
import org.example.usermanagementsystem.dto.Request.UserRequest;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;
import org.example.usermanagementsystem.dto.Request.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.usermanagementsystem.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a user with name and email")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @PostMapping("/{id}/deposit")
    @Operation(summary = "Deposit money", description = "Adds balance to the user's account")
    public ResponseEntity<UserResponse> deposit(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(userService.deposit(id, depositRequest.getAmount()));
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Get user transactions", description = "Fetches transaction history of the user")
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(
            @Parameter(description = "User ID") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserTransactions(id));
    }
}
