package org.example.usermanagementsystem.dto.Request.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private Double amount;
    private LocalDateTime timestamp;
}
