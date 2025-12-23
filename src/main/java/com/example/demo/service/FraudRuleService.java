package com.example.demo.service;

import com.example.demo.model.FraudRule;
import com.example.demo.repository.FraudRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FraudRuleService {

    private final FraudRuleRepository fraudRuleRepository;

    public FraudRuleService(FraudRuleRepository fraudRuleRepository) {
        this.fraudRuleRepository = fraudRuleRepository;
    }

    public FraudRule addRule(FraudRule rule) {
        return fraudRuleRepository.save(rule);
    }

    public List<FraudRule> getAllRules() {
        return fraudRuleRepository.findAll();
    }
}