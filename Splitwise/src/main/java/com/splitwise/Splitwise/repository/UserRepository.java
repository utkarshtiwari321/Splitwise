package com.splitwise.Splitwise.repository;

import com.splitwise.Splitwise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}