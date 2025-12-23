package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Claim;
import com.example.demo.model.FraudCheckResult;
import com.example.demo.model.FraudRule;
import com.example.demo.repository.ClaimRepository;
import com.example.demo.repository.FraudCheckResultRepository;
import com.example.demo.repository.FraudRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FraudDetectionService {

    private final ClaimRepository claimRepository;
    private final FraudRuleRepository fraudRuleRepository;
    private final FraudCheckResultRepository fraudCheckResultRepository;

    // Strict Constructor Injection Order
    public FraudDetectionService(ClaimRepository claimRepository, 
                                 FraudRuleRepository fraudRuleRepository, 
                                 FraudCheckResultRepository fraudCheckResultRepository) {
        this.claimRepository = claimRepository;
        this.fraudRuleRepository = fraudRuleRepository;
        this.fraudCheckResultRepository = fraudCheckResultRepository;
    }

    public FraudCheckResult evaluateClaim(Long claimId) {
        // 1. Fetch Claim
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        // 2. Fetch All Rules
        List<FraudRule> rules = fraudRuleRepository.findAll();

        // 3. Initialize Result
        FraudCheckResult result = new FraudCheckResult();
        result.setClaim(claim);
        result.setIsFraudulent(false);
        result.setRejectionReason("No fraud detected");
        result.setTriggeredRuleName(null);

        // 4. Iterate and Check Rules
        for (FraudRule rule : rules) {
            if (matchesCondition(claim, rule)) {
                result.setIsFraudulent(true);
                result.setTriggeredRuleName(rule.getRuleName());
                result.setRejectionReason("Violated rule: " + rule.getRuleName() + " (Severity: " + rule.getSeverity() + ")");
                break; // Stop at first violation for this simple implementation
            }
        }

        // 5. Save and Return
        return fraudCheckResultRepository.save(result);
    }

    public FraudCheckResult getResultByClaim(Long claimId) {
        return fraudCheckResultRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found for claim: " + claimId));
    }

    // Helper logic to parse string operators from DB
    private boolean matchesCondition(Claim claim, FraudRule rule) {
        if ("claimAmount".equalsIgnoreCase(rule.getConditionField())) {
            double claimAmount = claim.getClaimAmount();
            double ruleValue = Double.parseDouble(rule.getValue());

            switch (rule.getOperator()) {
                case ">": return claimAmount > ruleValue;
                case ">=": return claimAmount >= ruleValue;
                case "<": return claimAmount < ruleValue;
                case "<=": return claimAmount <= ruleValue;
                case "=": return claimAmount == ruleValue;
                default: return false;
            }
        }
        // You can add more field checks here (e.g., description keywords)
        return false;
    }
}