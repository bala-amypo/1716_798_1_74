package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Claim;
import com.example.demo.model.FraudCheckResult;
import com.example.demo.model.FraudRule;
import com.example.demo.repository.ClaimRepository;
import com.example.demo.repository.FraudCheckResultRepository;
import com.example.demo.repository.FraudRuleRepository;
import com.example.demo.service.FraudDetectionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final ClaimRepository claimRepository;
    private final FraudRuleRepository fraudRuleRepository;
    private final FraudCheckResultRepository resultRepository;

    public FraudDetectionServiceImpl(ClaimRepository claimRepository, 
                                     FraudRuleRepository fraudRuleRepository, 
                                     FraudCheckResultRepository resultRepository) {
        this.claimRepository = claimRepository;
        this.fraudRuleRepository = fraudRuleRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public FraudCheckResult evaluateClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        List<FraudRule> rules = fraudRuleRepository.findAll();
        FraudCheckResult result = new FraudCheckResult();
        result.setClaim(claim);
        result.setIsFraudulent(false);
        result.setRejectionReason("No fraud detected");

        // Logic: Iterate rules and check conditions
        for (FraudRule rule : rules) {
            if (isRuleViolated(claim, rule)) {
                result.setIsFraudulent(true);
                result.setTriggeredRuleName(rule.getRuleName());
                result.setRejectionReason("Violated rule: " + rule.getRuleName());
                
                // Add to Many-to-Many set in Claim (as required by tests)
                claim.getSuspectedRules().add(rule);
                break; // Stop at first violation
            }
        }
        
        // Populate specific field for 3NF test
        String matchedRules = claim.getSuspectedRules().stream()
                                   .map(FraudRule::getRuleName)
                                   .collect(Collectors.joining(","));
        result.setMatchedRules(matchedRules);

        claimRepository.save(claim); // Update claim with new suspected rules
        return resultRepository.save(result);
    }

    @Override
    public FraudCheckResult getResultByClaim(Long claimId) {
        return resultRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found for claim: " + claimId));
    }

    private boolean isRuleViolated(Claim claim, FraudRule rule) {
        if ("claimAmount".equalsIgnoreCase(rule.getConditionField())) {
            double claimVal = claim.getClaimAmount();
            double ruleVal = Double.parseDouble(rule.getValue());
            String op = rule.getOperator();
            
            if (">".equals(op)) return claimVal > ruleVal;
            if (">=".equals(op)) return claimVal >= ruleVal;
            if ("<".equals(op)) return claimVal < ruleVal;
            if ("<=".equals(op)) return claimVal <= ruleVal;
            if ("=".equals(op)) return claimVal == ruleVal;
        }
        return false;
    }
}