package com.example.demo.repository;

import com.example.demo.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    // Finds a specific policy by its unique number
    Optional<Policy> findByPolicyNumber(String policyNumber);

    // Finds all policies belonging to a specific user ID
    List<Policy> findByUserId(Long userId);
}