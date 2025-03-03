// ExpenseService.java
package com.splitwise.Splitwise.service;

import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.model.*;
import com.splitwise.Splitwise.repository.*;
import com.splitwise.Splitwise.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Transactional
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        // Validate expense
        if (expenseDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Expense amount must be positive");
        }

        if (expenseDTO.getSplits() == null || expenseDTO.getSplits().isEmpty()) {
            throw new BadRequestException("At least one split must be provided");
        }

        // Validate total split amount matches expense amount
        BigDecimal totalSplitAmount = expenseDTO.getSplits().stream()
                .map(SplitDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (expenseDTO.getAmount().compareTo(totalSplitAmount) != 0) {
            throw new BadRequestException("Sum of split amounts must equal the expense amount");
        }

        // Get payer
        User payer = userRepository.findById(expenseDTO.getPayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Payer not found with id " + expenseDTO.getPayerId()));

        // Create expense
        Expense expense = new Expense();
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setPayer(payer);
        expense.setCreatedAt(LocalDateTime.now());

        Expense savedExpense = expenseRepository.save(expense);

        // Create splits
        List<Split> splits = new ArrayList<>();
        for (SplitDTO splitDTO : expenseDTO.getSplits()) {
            User user = userRepository.findById(splitDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + splitDTO.getUserId()));

            Split split = new Split();
            split.setExpense(savedExpense);
            split.setUser(user);
            split.setAmount(splitDTO.getAmount());
            splits.add(split);
        }

        splitRepository.saveAll(splits);
        savedExpense.setSplits(splits);

        // Map to DTO
        ExpenseDTO savedExpenseDTO = new ExpenseDTO();
        savedExpenseDTO.setId(savedExpense.getId());
        savedExpenseDTO.setDescription(savedExpense.getDescription());
        savedExpenseDTO.setAmount(savedExpense.getAmount());
        savedExpenseDTO.setPayerId(savedExpense.getPayer().getId());

        List<SplitDTO> savedSplitDTOs = splits.stream()
                .map(split -> {
                    SplitDTO splitDTO = new SplitDTO();
                    splitDTO.setUserId(split.getUser().getId());
                    splitDTO.setAmount(split.getAmount());
                    return splitDTO;
                })
                .toList();

        savedExpenseDTO.setSplits(savedSplitDTOs);

        return savedExpenseDTO;
    }
}