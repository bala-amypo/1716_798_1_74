package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Claim;
import com.example.demo.model.Policy;
import com.example.demo.repository.ClaimRepository;
import com.example.demo.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    public ClaimService(ClaimRepository claimRepository, PolicyRepository policyRepository) {
        this.claimRepository = claimRepository;
        this.policyRepository = policyRepository;
    }

    public Claim createClaim(Claim claim) {
        // Validation Rule: Claim amount must be > 0
        if (claim.getClaimAmount() <= 0) {
            throw new IllegalArgumentException("Invalid claim: Amount must be greater than 0");
        }
        // Validation Rule: Claim date cannot be in the future
        if (claim.getClaimDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid claim: Date cannot be in the future");
        }

        // Link policy
        if (claim.getPolicy() != null && claim.getPolicy().getId() != null) {
            Policy policy = policyRepository.findById(claim.getPolicy().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));
            claim.setPolicy(policy);
        }

        return claimRepository.save(claim);
    }

    public Claim getClaim(Long claimId) {
        return claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}