// SettlementService.java
package com.splitwise.Splitwise.service;

import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.exception.*;
import com.splitwise.Splitwise.model.*;
import com.splitwise.Splitwise.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceService balanceService;

    @Transactional
    public SettlementDTO createSettlement(SettlementDTO settlementDTO) {
        // Validate settlement
        if (settlementDTO.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Settlement amount must be positive");
        }

        if (settlementDTO.getFromUserId().equals(settlementDTO.getToUserId())) {
            throw new BadRequestException("Cannot settle with yourself");
        }

        // Get users
        User fromUser = userRepository.findById(settlementDTO.getFromUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + settlementDTO.getFromUserId()));

        User toUser = userRepository.findById(settlementDTO.getToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + settlementDTO.getToUserId()));

        // Create settlement
        Settlement settlement = new Settlement();
        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);
        settlement.setAmount(settlementDTO.getAmount());
        settlement.setSettledAt(LocalDateTime.now());

        Settlement savedSettlement = settlementRepository.save(settlement);

        // Map to DTO
        SettlementDTO savedSettlementDTO = new SettlementDTO();
        savedSettlementDTO.setFromUserId(savedSettlement.getFromUser().getId());
        savedSettlementDTO.setToUserId(savedSettlement.getToUser().getId());
        savedSettlementDTO.setAmount(savedSettlement.getAmount());

        return savedSettlementDTO;
    }
}