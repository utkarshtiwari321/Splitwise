package com.splitwise.Splitwise.repository;

import com.splitwise.Splitwise.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
