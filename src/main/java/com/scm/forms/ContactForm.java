package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Email Address is required")
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$",message="Invalid Phone Number")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message="Description is required")
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;
    // will use custom annotaion to validate type, size, resolution of image 
    private MultipartFile contactImage;
}
