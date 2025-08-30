package com.indigo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "passenger")
@Data
@NoArgsConstructor
public class Passenger{
    @Id
    private String passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int age;
}
