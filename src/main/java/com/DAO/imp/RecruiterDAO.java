package com.DAO.imp;

import com.DAO.inf.RecruiterDaoInterface;
import com.entity.Application;
import com.entity.JobOffer;
import com.entity.Recruiter;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class RecruiterDAO implements RecruiterDaoInterface {
    private EntityManager entityManager;
    @Override
    public List<Recruiter> getAllRecruiters() {
        return Collections.emptyList();
    }

    @Override
    public void saveRecruiter(Recruiter recruiter) {

    }

    @Override
    public void deleteRecruiter(int recruiterId) {

    }

    @Override
    public void updateRecruiter(Recruiter recruiter) {

    }

    @Override
    public Recruiter getRecruiterById(int id) {
        return null;
    }

    public void saveApplication(Application application) {
        entityManager.getTransaction().begin();
        entityManager.persist(application);
        entityManager.getTransaction().commit();
    }
    public void saveJobOffer(JobOffer jobOffer) {
        entityManager.getTransaction().begin();
        entityManager.persist(jobOffer);
        entityManager.getTransaction().commit();
    }
    public void updateApplication(Application application) {
        entityManager.getTransaction().begin();
        entityManager.merge(application);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateJobOffer(JobOffer jobOffer) {

    }

    @Override
    public void deleteJobOffer(int jobOfferId) {

    }

    @Override
    public List<Application> getAllApplications() {
        return Collections.emptyList();
    }

    @Override
    public List<JobOffer> getAllJobOffers() {
        return Collections.emptyList();
    }

    @Override
    public List<Application> filterApplicationByStatus(String status) {
        return Collections.emptyList();
    }

    @Override
    public List<Application> filterApplicationBySkill(String skill) {
        return Collections.emptyList();
    }

    public Application getApplicationById(int applicationId) {
        return entityManager.find(Application.class, applicationId);
    }

    @Override
    public JobOffer getJobOfferById(int jobOfferId) {
        return null;
    }

    @Override
    public void close() {

    }
}
