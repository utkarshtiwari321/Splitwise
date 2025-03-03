package com.splitwise.Splitwise.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BalanceDTO {
    private Long userId;
    private String userName;
    private BigDecimal amount; // Positive means user is owed money, negative means user owes money
}