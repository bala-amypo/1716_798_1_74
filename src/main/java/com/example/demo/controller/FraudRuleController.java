package com.example.demo.controller;

import com.example.demo.model.FraudRule;
import com.example.demo.service.FraudRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class FraudRuleController {

    private final FraudRuleService fraudRuleService;

    public FraudRuleController(FraudRuleService fraudRuleService) {
        this.fraudRuleService = fraudRuleService;
    }

    @PostMapping
    public ResponseEntity<FraudRule> addRule(@RequestBody FraudRule rule) {
        return ResponseEntity.ok(fraudRuleService.addRule(rule));
    }
    
    // Note: If you add 'getAllRules()' to your FraudRuleService interface later,
    // you can uncomment a @GetMapping here.
}