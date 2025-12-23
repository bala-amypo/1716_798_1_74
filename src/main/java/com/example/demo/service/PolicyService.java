package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Policy;
import com.example.demo.model.User;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;

    public PolicyService(PolicyRepository policyRepository, UserRepository userRepository) {
        this.policyRepository = policyRepository;
        this.userRepository = userRepository;
    }

    public Policy createPolicy(Policy policy) {
        // Validation: End date must be after start date
        if (policy.getEndDate() != null && policy.getStartDate() != null && 
            policy.getEndDate().isBefore(policy.getStartDate())) {
            throw new IllegalArgumentException("Invalid date range: End date must be after start date");
        }

        // We need to fetch the full User entity to link it properly
        if (policy.getUser() != null && policy.getUser().getId() != null) {
            User user = userRepository.findById(policy.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            policy.setUser(user);
        }

        return policyRepository.save(policy);
    }

    public List<Policy> getPoliciesByUser(Long userId) {
        // Verify user exists first
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return policyRepository.findByUserId(userId);
    }
}