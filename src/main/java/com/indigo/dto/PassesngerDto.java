package com.indigo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PassesngerDto {

    @Pattern(regexp = "^[A-Za-z0-9]+(-[A-Za-z0-9]+)?$", message = "passengerId must be alphanumeric and may contain one hyphen")
    @Size(min = 5, max = 11, message = "passengerId must be up to 11 characters")
    private String passengerId;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^(\\+\\d+\\s)?\\d{10}$")
    private String phoneNumber;
    private int age;
}
