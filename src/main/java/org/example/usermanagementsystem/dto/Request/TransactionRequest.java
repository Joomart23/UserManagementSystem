package org.example.usermanagementsystem.dto.Request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Long fromUserId;
    private Long toUserId;
    private Double amount;
}

