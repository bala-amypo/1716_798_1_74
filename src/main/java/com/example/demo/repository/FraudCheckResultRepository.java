package com.example.demo.repository;

import com.example.demo.model.FraudCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FraudCheckResultRepository extends JpaRepository<FraudCheckResult, Long> {
    // Finds the fraud check result associated with a specific claim ID
    Optional<FraudCheckResult> findByClaimId(Long claimId);
}