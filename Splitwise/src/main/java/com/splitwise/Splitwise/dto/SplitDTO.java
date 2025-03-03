package com.splitwise.Splitwise.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SplitDTO {
    private Long userId;
    private BigDecimal amount;
}