package org.example.usermanagementsystem.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;
import org.example.usermanagementsystem.entity.Transaction;
import org.example.usermanagementsystem.enums.TransactionType;
import org.example.usermanagementsystem.entity.User;
import org.example.usermanagementsystem.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.example.usermanagementsystem.repository.TransactionRepository;
import org.example.usermanagementsystem.repository.UserRepository;
import org.example.usermanagementsystem.service.TransactionService;
import java.time.LocalDateTime;



@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    @Transactional
    public TransactionResponse transfer(Long fromUserId, Long toUserId, Double amount) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new NotFoundException("Жөнөтүүчү табылган жок!"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new NotFoundException("Алуучу табылган жок!"));

        if (fromUser.getBalance() < amount) {
            throw new RuntimeException("Жетишсиз баланс!");
        }

        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);

        userRepository.save(fromUser);
        userRepository.save(toUser);

        entityManager.flush();

        Transaction transaction = new Transaction();
        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.TRANSFER);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponse(savedTransaction.getId(), fromUserId, toUserId, amount, savedTransaction.getTimestamp());
    }

   }