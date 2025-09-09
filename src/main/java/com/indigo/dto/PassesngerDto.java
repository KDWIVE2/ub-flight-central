package com.indigo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class PassesngerDto implements Serializable {

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

    public PassesngerDto() {
    }

    public PassesngerDto(String passengerId, String firstName, String lastName, String email, String phoneNumber, int age) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public @Pattern(regexp = "^[A-Za-z0-9]+(-[A-Za-z0-9]+)?$", message = "passengerId must be alphanumeric and may contain one hyphen") @Size(min = 5, max = 11, message = "passengerId must be up to 11 characters") String getPassengerId() {
        return passengerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public @Email String getEmail() {
        return email;
    }

    public @Pattern(regexp = "^(\\+\\d+\\s)?\\d{10}$") String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setPassengerId(@Pattern(regexp = "^[A-Za-z0-9]+(-[A-Za-z0-9]+)?$", message = "passengerId must be alphanumeric and may contain one hyphen") @Size(min = 5, max = 11, message = "passengerId must be up to 11 characters") String passengerId) {
        this.passengerId = passengerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public void setPhoneNumber(@Pattern(regexp = "^(\\+\\d+\\s)?\\d{10}$") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PassesngerDto{" +
                "passengerId='" + passengerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                '}';
    }
}

