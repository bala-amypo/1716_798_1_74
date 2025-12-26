package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String conditionField;
    private String operator;
    private String value;
    private String severity;

    // Bidirectional mapping for test
    @ManyToMany(mappedBy = "suspectedRules")
    private Set<Claim> claims = new HashSet<>();

    public FraudRule(String ruleName, String conditionField, String operator, String value, String severity) {
        this.ruleName = ruleName;
        this.conditionField = conditionField;
        this.operator = operator;
        this.value = value;
        this.severity = severity;
    }
}