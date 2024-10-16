package com.DAO.imp;

import com.entity.Evaluation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EvaluationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Evaluation> getPendingEvaluationsWithin(int days) {
        String queryStr = "SELECT e FROM Evaluation e WHERE e.dueDate BETWEEN CURRENT_DATE AND CURRENT_DATE + :days";
        return entityManager.createQuery(queryStr, Evaluation.class)
                .setParameter("days", days)
                .getResultList();
    }
}
