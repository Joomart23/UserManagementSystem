package org.example.usermanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.usermanagementsystem.enums.TransactionType;

import java.time.LocalDateTime;

@Table(name = "transactions")
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_gen")
    @SequenceGenerator(name = "tran_gen", sequenceName = "tran_seq",allocationSize = 1)
    private Long id;

    private Double amount;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "id")
    private User toUser;

}