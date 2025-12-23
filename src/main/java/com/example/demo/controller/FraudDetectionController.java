package com.example.demo.controller;

import com.example.demo.model.FraudCheckResult;
import com.example.demo.service.FraudDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud-check")
public class FraudDetectionController {

    private final FraudDetectionService service;

    public FraudDetectionController(FraudDetectionService service) {
        this.service = service;
    }

    @PostMapping("/evaluate/{claimId}")
    public ResponseEntity<FraudCheckResult> evaluateClaim(@PathVariable Long claimId) {
        return ResponseEntity.ok(service.evaluateClaim(claimId));
    }

    @GetMapping("/result/claim/{claimId}")
    public ResponseEntity<FraudCheckResult> getResult(@PathVariable Long claimId) {
        return ResponseEntity.ok(service.getResultByClaim(claimId));
    }
}