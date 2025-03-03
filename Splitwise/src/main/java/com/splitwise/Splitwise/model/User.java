package com.splitwise.Splitwise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")  // Renaming the table to avoid keyword conflict
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "payer")
    private List<Expense> expenses = new ArrayList<>();
}