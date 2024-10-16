package com.DAO.imp;

import com.DAO.inf.VacationDaoInterface;
import com.entity.Employee;
import com.entity.Vacation;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VacationDAO implements VacationDaoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public VacationDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeePU");
        this.entityManager = emf.createEntityManager();
    }

    @Transactional
    public void saveVacation(Vacation vacation) {
        entityManager.persist(vacation);
    }

    @Transactional
    public void deleteVacation(int vacationId) {
        Vacation vacation = entityManager.find(Vacation.class, vacationId);
        if (vacation != null) {
            entityManager.remove(vacation);
        }
    }
    @Transactional
    public List<Vacation> getVacationsBetween(Date startDate, Date endDate) {
        return entityManager.createQuery("SELECT v FROM Vacation v WHERE v.startDate >= :startDate AND v.endDate <= :endDate", Vacation.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }


    @Transactional
     public void updateVacation(Vacation vacation) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin(); // Start the transaction

            entityManager.merge(vacation); // Perform the update
            entityManager.flush();  // Flush the changes to the database

            transaction.commit(); // Commit the transaction
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Rollback if something goes wrong
            }
            throw e;  // Re-throw the exception for higher-level handling
        }
    }


    public Vacation getVacationById(int id) {
        return entityManager.find(Vacation.class, id);
    }

    public List<Vacation> getLeaveHistoryByEmployee(Employee employee) {
        return entityManager.createQuery("SELECT lr FROM Vacation lr WHERE lr.employee = :employee", Vacation.class)
                .setParameter("employee", employee)
                .getResultList();
    }

    public List<Vacation> getAllVacations() {
        try {
            entityManager.getTransaction().begin();
            List<Vacation> vacations = entityManager.createQuery("SELECT v FROM Vacation v", Vacation.class).getResultList();
            entityManager.getTransaction().commit();
            System.out.println("here");

            return vacations;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}

