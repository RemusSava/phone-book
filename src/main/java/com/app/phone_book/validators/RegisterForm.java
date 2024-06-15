package com.app.phone_book.validators;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RegisterForm {
    @Email(message = "Invalid email address")
    @NotNull(message = "Email cannot be empty")
    private String email;

    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Confirm Password cannot be empty")
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
