package com.splitwise.Splitwise.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SettlementDTO {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}