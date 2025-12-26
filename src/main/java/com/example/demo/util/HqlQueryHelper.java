package com.example.demo.util;

import com.example.demo.model.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HqlQueryHelper {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Requirement: Use HQL to find claims containing a specific keyword description.
     * Logic: Case-insensitive search using LIKE.
     */
    public List<Claim> findClaimsByDescriptionKeyword(String keyword) {
        String hql = "FROM Claim c WHERE LOWER(c.description) LIKE LOWER(:keyword)";
        TypedQuery<Claim> query = entityManager.createQuery(hql, Claim.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    /**
     * Requirement: Use HQL to find claims exceeding a certain amount.
     */
    public List<Claim> findHighValueClaims(Double minAmount) {
        String hql = "FROM Claim c WHERE c.claimAmount > :minAmount";
        TypedQuery<Claim> query = entityManager.createQuery(hql, Claim.class);
        query.setParameter("minAmount", minAmount);
        return query.getResultList();
    }
}