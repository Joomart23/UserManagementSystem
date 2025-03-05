package org.example.usermanagementsystem.service.impl;
import lombok.RequiredArgsConstructor;
import org.example.usermanagementsystem.dto.Request.UserRequest;
import org.example.usermanagementsystem.dto.Request.response.TransactionResponse;
import org.example.usermanagementsystem.dto.Request.response.UserResponse;
import org.example.usermanagementsystem.entity.Transaction;
import org.example.usermanagementsystem.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.usermanagementsystem.exception.NotFoundException;
import org.example.usermanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.example.usermanagementsystem.repository.UserRepository;
import org.example.usermanagementsystem.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
        private final TransactionRepository transactionRepository;
        public UserResponse createUser(UserRequest request) {
            try {
                if (request.getName() == null || request.getName().isEmpty()) {
                    throw new IllegalArgumentException("Имя не может быть пустым");
                }
                if (request.getEmail() == null || request.getEmail().isEmpty()) {
                    throw new IllegalArgumentException("Email не может быть пустым");
                }
                User user = new User();
                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setBalance(request.getBalance());

                User savedUser = userRepository.save(user);

                return toUserResponse(savedUser);
            } catch (IllegalArgumentException e) {
                log.error("Ошибка при валидации данных пользователя", e);
                throw e;
            } catch (Exception e) {
                log.error("Ошибка при создании пользователя", e);
                throw new RuntimeException("Ошибка при создании пользователя", e);
            }
    }
        public UserResponse deposit(Long id, Double amount) {
            if (amount == null || amount <= 0) {
                throw new IllegalArgumentException("Сума 0 же терс боло албайт!");
            }

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Колдонуучу табылган жок!"));

            user.setBalance(user.getBalance() + amount);
            User updatedUser = userRepository.save(user);

            return toUserResponse(updatedUser);
        }
        public List<TransactionResponse> getUserTransactions(Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Колдонуучу табылган жок!"));

            List<Transaction> transactions = transactionRepository.findByFromUserOrToUser(user);
            return transactions.stream().map(this::toTransactionResponse).collect(Collectors.toList());
        }


        private UserResponse toUserResponse(User user) {
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getBalance());
        }

        private TransactionResponse toTransactionResponse(Transaction transaction) {
            return new TransactionResponse(
                    transaction.getId(),
                    transaction.getFromUser().getId(),
                    transaction.getToUser().getId(),
                    transaction.getAmount(),
                    transaction.getTimestamp()
            );
        }
    }
