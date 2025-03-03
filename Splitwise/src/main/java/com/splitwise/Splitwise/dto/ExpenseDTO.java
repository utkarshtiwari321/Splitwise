package com.splitwise.Splitwise.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ExpenseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Long payerId;
    private List<SplitDTO> splits;
}