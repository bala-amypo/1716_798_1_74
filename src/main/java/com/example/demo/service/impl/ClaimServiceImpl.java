package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Claim;
import com.example.demo.model.Policy;
import com.example.demo.repository.ClaimRepository;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.service.ClaimService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository, PolicyRepository policyRepository) {
        this.claimRepository = claimRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public Claim createClaim(Long policyId, Claim claim) {
        // Validation: Cannot be in future
        if (claim.getClaimDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Claim date cannot be in the future");
        }
        // Validation: Cannot be negative
        if (claim.getClaimAmount() < 0) {
            throw new IllegalArgumentException("Claim amount cannot be negative");
        }

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + policyId));
        claim.setPolicy(policy);
        
        return claimRepository.save(claim);
    }

    @Override
    public Claim getClaim(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + id));
    }
}