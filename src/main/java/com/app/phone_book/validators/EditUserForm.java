package com.app.phone_book.validators;

import com.app.phone_book.models.User;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class EditUserForm {

    private UUID id;
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    private String password;
    @NotNull(message = "Role cannot be empty")
    private UUID roleId;

    public static User convertToUser(EditUserForm userData){
        User user = new User();
        user.setEmail(userData.email);
        user.setPassword(userData.password);

        return user;
    }
}
