// BalanceService.java
package com.splitwise.Splitwise.service;

import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.exception.*;
import com.splitwise.Splitwise.model.*;
import com.splitwise.Splitwise.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    public List<BalanceDTO> getUserBalances(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // Get all users
        List<User> allUsers = userRepository.findAll();

        // Calculate balances
        Map<Long, BigDecimal> balances = calculateBalances(user, allUsers);

        // Convert to DTOs
        List<BalanceDTO> balanceDTOs = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> entry : balances.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) != 0 && !entry.getKey().equals(userId)) {
                User otherUser = userRepository.findById(entry.getKey())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + entry.getKey()));

                BalanceDTO balanceDTO = new BalanceDTO();
                balanceDTO.setUserId(otherUser.getId());
                balanceDTO.setUserName(otherUser.getName());
                balanceDTO.setAmount(entry.getValue());

                balanceDTOs.add(balanceDTO);
            }
        }

        return balanceDTOs;
    }

    private Map<Long, BigDecimal> calculateBalances(User user, List<User> allUsers) {
        Map<Long, BigDecimal> balances = new HashMap<>();

        // Initialize balances for all users
        for (User u : allUsers) {
            balances.put(u.getId(), BigDecimal.ZERO);
        }

        // Get all splits where user is involved
        List<Split> userSplits = splitRepository.findByUser(user);

        // Calculate balances from expenses where user is the payer
        for (Expense expense : user.getExpenses()) {
            for (Split split : expense.getSplits()) {
                if (!split.getUser().getId().equals(user.getId())) {
                    // If someone else owes money to the user
                    BigDecimal currentBalance = balances.get(split.getUser().getId());
                    balances.put(split.getUser().getId(), currentBalance.add(split.getAmount()));
                }
            }
        }

        // Calculate balances from expenses where user is a participant but not the payer
        for (Split split : userSplits) {
            if (!split.getExpense().getPayer().getId().equals(user.getId())) {
                // If user owes money to someone else
                BigDecimal currentBalance = balances.get(split.getExpense().getPayer().getId());
                balances.put(split.getExpense().getPayer().getId(), currentBalance.subtract(split.getAmount()));
            }
        }

        // Adjust balances based on settlements
        List<Settlement> settlements = settlementRepository.findByFromUserOrToUser(user, user);

        for (Settlement settlement : settlements) {
            if (settlement.getFromUser().getId().equals(user.getId())) {
                // User paid someone
                BigDecimal currentBalance = balances.get(settlement.getToUser().getId());
                balances.put(settlement.getToUser().getId(), currentBalance.subtract(settlement.getAmount()));
            } else if (settlement.getToUser().getId().equals(user.getId())) {
                // User was paid by someone
                BigDecimal currentBalance = balances.get(settlement.getFromUser().getId());
                balances.put(settlement.getFromUser().getId(), currentBalance.add(settlement.getAmount()));
            }
        }

        return balances;
    }
}