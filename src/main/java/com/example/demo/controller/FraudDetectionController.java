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

    // NOTE: The FraudDetectionService interface we generated for the TEST 
    // was a marker interface (empty). To make this controller work, 
    // you would normally add methods like 'evaluateClaim(Long id)' 
    // to the FraudDetectionService interface.
    
    // Once you add 'evaluateClaim' to the interface, you can uncomment this:
    /*
    @PostMapping("/evaluate/{claimId}")
    public ResponseEntity<FraudCheckResult> evaluateClaim(@PathVariable Long claimId) {
        return ResponseEntity.ok(service.evaluateClaim(claimId));
    }
    */
}