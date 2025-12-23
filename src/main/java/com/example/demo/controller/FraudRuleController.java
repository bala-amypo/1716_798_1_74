package com.example.demo.controller;

import com.example.demo.model.FraudRule;
import com.example.demo.service.FraudRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<FraudRule>> listRules() {
        return ResponseEntity.ok(fraudRuleService.getAllRules());
    }
}