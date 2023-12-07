package com.example.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpForm {

    @NotEmpty
    String name;

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    String password;

    @NotEmpty
    String confirmPassword;
}
