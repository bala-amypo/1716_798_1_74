package com.example.demo.repository;

import com.example.demo.model.FraudRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudRuleRepository extends JpaRepository<FraudRule, Long> {
    // JpaRepository provides findAll() by default, so no extra code is needed here.
    // However, you can add findByRuleName if you want to enforce unique names.
    boolean existsByRuleName(String ruleName);
}