package org.example.usermanagementsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.usermanagementsystem.dto.Request.TransactionRequest;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;
import org.example.usermanagementsystem.exception.ExceptionResponse;
import org.example.usermanagementsystem.exception.NotFoundException;
import org.example.usermanagementsystem.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer money between users",
            description = "Transfers a specified amount from one user to another.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful transfer", content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request or insufficient balance", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<?> transfer(@RequestBody TransactionRequest request) {
        Long fromUserId = request.getFromUserId();
        Long toUserId = request.getToUserId();
        Double amount = request.getAmount();

        try {
            logger.info("Transfer request: fromUserId={}, toUserId={}, amount={}", fromUserId, toUserId, amount);

            TransactionResponse transactionResponse = transactionService.transfer(fromUserId, toUserId, amount);

            logger.info("Transfer successful: {}", transactionResponse);
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK);

        } catch (IllegalArgumentException | NotFoundException e) {
            logger.error("Error during transfer: {}", e.getMessage());

            ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } catch (RuntimeException e) {
            logger.error("Error during transfer: {}", e.getMessage());

            ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), "Insufficient balance: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}