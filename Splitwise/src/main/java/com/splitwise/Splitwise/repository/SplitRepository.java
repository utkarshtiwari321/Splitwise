package com.splitwise.Splitwise.repository;

import com.splitwise.Splitwise.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SplitRepository extends JpaRepository<Split, Long> {
    List<Split> findByUser(User user);
}
