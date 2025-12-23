package com.example.demo.service;

import com.example.demo.model.Policy;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
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
        // Ensure user exists
        if (policy.getUser() != null && policy.getUser().getId() != null) {
            var user = userRepository.findById(policy.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            policy.setUser(user);
        }
        return policyRepository.save(policy);
    }

    public List<Policy> getPoliciesByUser(Long userId) {
        return policyRepository.findByUserId(userId);
    }
}    