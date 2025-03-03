package com.splitwise.Splitwise.repository;

import com.splitwise.Splitwise.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findByFromUserOrToUser(User fromUser, User toUser);
}