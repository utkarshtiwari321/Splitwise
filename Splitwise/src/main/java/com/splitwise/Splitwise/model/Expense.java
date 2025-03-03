package com.splitwise.Splitwise.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<Split> splits = new ArrayList<>();
}
