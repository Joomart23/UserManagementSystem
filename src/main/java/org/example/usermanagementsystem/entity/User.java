package org.example.usermanagementsystem.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Table(name = "users")
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    @Min(0)
    private Double balance;

    @OneToMany(mappedBy = "toUser" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> receivedTransactions;


}
