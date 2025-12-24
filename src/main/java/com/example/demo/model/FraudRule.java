package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FraudRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ruleName;

    private String conditionField; // e.g., "claimAmount"
    private String operator;       // e.g., ">"
    private String value;          // e.g., "10000"
    private String severity;       // LOW, MEDIUM, HIGH
}