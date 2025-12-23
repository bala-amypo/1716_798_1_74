package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Claim;
import com.example.demo.repository.ClaimRepository;
import com.example.demo.repository.PolicyRepository;
import org.springframework.stereotype.Service;
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
        // Ensure the policy exists before linking
        if (claim.getPolicy() != null && claim.getPolicy().getId() != null) {
            var policy = policyRepository.findById(claim.getPolicy().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));
            claim.setPolicy(policy);
        }
        return claimRepository.save(claim);
    }

    public Claim getClaim(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }
    
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}