package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String conditionField; // e.g. "claimAmount"
    private String operator;       // e.g. ">"
    private String value;          // e.g. "10000"
    private String severity;       // "LOW", "MEDIUM", "HIGH"

    // Bidirectional Many-to-Many mapping required by tests
    @ManyToMany(mappedBy = "suspectedRules")
    @ToString.Exclude // Prevent circular reference in toString()
    private Set<Claim> claims = new HashSet<>();

    // Constructor required by DemoApplicationTests
    public FraudRule(String ruleName, String conditionField, String operator, String value, String severity) {
        this.ruleName = ruleName;
        this.conditionField = conditionField;
        this.operator = operator;
        this.value = value;
        this.severity = severity;
    }
}