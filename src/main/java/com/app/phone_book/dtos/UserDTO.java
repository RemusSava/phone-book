package com.app.phone_book.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String role;
    private String createdAt;
    private String updatedAt;
}
