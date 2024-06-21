package com.app.phone_book.validators;

import com.app.phone_book.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class EditUserForm {
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;

    @NotEmpty(message = "Role cannot be empty")
    private UUID roleId;

    public static User convertToUser(EditUserForm userData){
        User user = new User();
        user.setEmail(userData.email);
        user.setPassword(userData.password);

        return user;
    }
}
