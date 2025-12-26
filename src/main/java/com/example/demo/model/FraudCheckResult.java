package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class FraudCheckResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Claim claim;

    private Boolean isFraudulent;
    private String triggeredRuleName;
    private String rejectionReason;
    
    // Test requires access to matchedRules string
    private String matchedRules;
    private LocalDateTime checkedAt;
}