package com.example.demo.controller;

import com.example.demo.model.Policy;
import com.example.demo.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Policy> createPolicy(@PathVariable Long userId, @RequestBody Policy policy) {
        return ResponseEntity.ok(policyService.createPolicy(userId, policy));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Policy>> listUserPolicies(@PathVariable Long userId) {
        return ResponseEntity.ok(policyService.getPoliciesByUser(userId));
    }
}