package com.example.demo.service.impl;

import com.example.demo.model.FraudRule;
import com.example.demo.repository.FraudRuleRepository;
import com.example.demo.service.FraudRuleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class FraudRuleServiceImpl implements FraudRuleService {

    private final FraudRuleRepository fraudRuleRepository;
    private static final Set<String> VALID_SEVERITIES = Set.of("LOW", "MEDIUM", "HIGH");

    public FraudRuleServiceImpl(FraudRuleRepository fraudRuleRepository) {
        this.fraudRuleRepository = fraudRuleRepository;
    }

    @Override
    public FraudRule addRule(FraudRule rule) {
        // Validation: Severity must be standard
        if (rule.getSeverity() != null && !VALID_SEVERITIES.contains(rule.getSeverity())) {
            throw new IllegalArgumentException("Invalid severity level: " + rule.getSeverity());
        }
        return fraudRuleRepository.save(rule);
    }

    @Override
    public List<FraudRule> getAllRules() {
        return fraudRuleRepository.findAll();
    }
}