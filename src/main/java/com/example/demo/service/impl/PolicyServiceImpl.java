package com.example.demo.service.impl;

import com.example.demo.model.Policy;
import com.example.demo.model.User;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PolicyService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository, UserRepository userRepository) {
        this.policyRepository = policyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Policy createPolicy(Long userId, Policy policy) {
        // Test Logic: Check invalid dates
        if (policy.getEndDate().isBefore(policy.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        // Test Logic: Check unique policy number
        if (policyRepository.existsByPolicyNumber(policy.getPolicyNumber())) {
            throw new IllegalArgumentException("Policy number already exists");
        }

        User user = userRepository.findById(userId).orElseThrow();
        policy.setUser(user);
        return policyRepository.save(policy);
    }

    @Override
    public List<Policy> getPoliciesByUser(Long userId) {
        return policyRepository.findByUserId(userId);
    }
}