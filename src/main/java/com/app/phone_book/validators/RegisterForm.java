package com.app.phone_book.validators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
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

    public boolean isPasswordsMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
