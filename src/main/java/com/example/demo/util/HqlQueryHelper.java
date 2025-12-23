package com.example.demo.util;

import com.example.demo.model.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HqlQueryHelper {

    // Inject the EntityManager directly as required
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Requirement: Returns claims where the description contains the keyword (case-insensitive).
     */
    public List<Claim> findClaimsByDescriptionKeyword(String keyword) {
        // HQL Query: Selects Claims where description is like the keyword (lowercased for case-insensitivity)
        String hql = "FROM Claim c WHERE LOWER(c.description) LIKE LOWER(:keyword)";
        
        TypedQuery<Claim> query = entityManager.createQuery(hql, Claim.class);
        query.setParameter("keyword", "%" + keyword + "%");
        
        return query.getResultList();
    }

    /**
     * Requirement: Returns claims where claimAmount > minAmount.
     */
    public List<Claim> findHighValueClaims(Double minAmount) {
        // HQL Query: Selects Claims strictly greater than the provided amount
        String hql = "FROM Claim c WHERE c.claimAmount > :minAmount";
        
        TypedQuery<Claim> query = entityManager.createQuery(hql, Claim.class);
        query.setParameter("minAmount", minAmount);
        
        return query.getResultList();
    }
}