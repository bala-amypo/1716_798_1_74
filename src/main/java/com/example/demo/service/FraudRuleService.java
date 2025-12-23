package com.example.demo.service;

import com.example.demo.model.FraudRule;
import com.example.demo.repository.FraudRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FraudRuleService {

    private final FraudRuleRepository repository;

    public FraudRuleService(FraudRuleRepository repository) {
        this.repository = repository;
    }

    public FraudRule addRule(FraudRule rule) {
        return repository.save(rule);
    }

    public List<FraudRule> getAllRules() {
        return repository.findAll();
    }
}