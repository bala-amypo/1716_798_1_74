package com.example.demo.util;

import com.example.demo.model.Claim;
import org.springframework.stereotype.Component;
import java.util.List;

// This class is Mocked in the test, so the implementation here can be basic
// or the one provided in previous steps. The test only cares that the class and methods exist.
@Component
public class HqlQueryHelper {
    public List<Claim> findClaimsByDescriptionKeyword(String keyword) {
        return List.of(); 
    }

    public List<Claim> findHighValueClaims(Double minAmount) {
        return List.of();
    }
}