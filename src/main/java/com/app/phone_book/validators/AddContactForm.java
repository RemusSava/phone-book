package com.app.phone_book.validators;

import com.app.phone_book.models.Contact;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class AddContactForm {

    private UUID id;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
    private String phone;

    @Email(message = "Invalid email address")
    private String email;

    private String address;
    private String jobTitle;
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double latitude;
    private UUID groupId;

    public static Contact convertToContact(AddContactForm contactData){
        Contact contact = new Contact();
        contact.setFirstName(contactData.firstName);
        contact.setLastName(contactData.lastName);
        contact.setEmail(contactData.email);
        contact.setPhone(contactData.phone);
        contact.setAddress(contactData.address);
        contact.setJobTitle(contactData.jobTitle);
        contact.setLongitude(contactData.longitude);
        contact.setLatitude(contactData.latitude);

        return contact;
    }
}
