package org.example.usermanagementsystem.dto.Request;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.usermanagementsystem.validation.EmailValidation;

@Getter
@Setter
public class UserRequest {

    @NotNull(message = "Name cannot be null")
    private String name;
    @EmailValidation
    private String email;
    private Double balance = 0.01;
}
