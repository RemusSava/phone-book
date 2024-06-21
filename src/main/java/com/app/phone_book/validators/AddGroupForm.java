package com.app.phone_book.validators;

import com.app.phone_book.models.Group;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class AddGroupForm {

    private UUID id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    public static Group convertToGroup(AddGroupForm groupData){
        Group group = new Group();
        group.setName(groupData.name);

        return group;
    }
}
