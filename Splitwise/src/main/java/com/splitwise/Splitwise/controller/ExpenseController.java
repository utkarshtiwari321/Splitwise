package com.splitwise.Splitwise.controller;

import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BalanceService balanceService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        return new ResponseEntity<>(expenseService.createExpense(expenseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/balances/{userId}")
    public ResponseEntity<List<BalanceDTO>> getUserBalances(@PathVariable Long userId) {
        return ResponseEntity.ok(balanceService.getUserBalances(userId));
    }
}