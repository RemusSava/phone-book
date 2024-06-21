package com.app.phone_book.validators;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RegisterForm {

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Confirm Password cannot be empty")
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
