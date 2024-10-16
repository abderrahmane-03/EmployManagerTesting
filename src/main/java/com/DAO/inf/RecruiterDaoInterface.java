package com.DAO.inf;

import com.entity.Application;
import com.entity.JobOffer;
import com.entity.Recruiter;

import java.util.List;

public interface RecruiterDaoInterface {
    public List<Application> getAllApplications();
    public List<JobOffer> getAllJobOffers();
    public List<Application> filterApplicationByStatus(String status);
    public List<Application> filterApplicationBySkill(String skill);
    public Application getApplicationById(int applicationId);
    public JobOffer getJobOfferById(int jobOfferId);
    public void updateApplication(Application application);
    public void updateJobOffer(JobOffer jobOffer);
    public void deleteJobOffer(int jobOfferId);
    public void saveJobOffer(JobOffer jobOffer);
    public void saveApplication(Application application);
    public List<Recruiter> getAllRecruiters();
    public void saveRecruiter(Recruiter recruiter);
    public void deleteRecruiter(int recruiterId);
    public void updateRecruiter(Recruiter recruiter);
    public Recruiter getRecruiterById(int id);
    public void close() ;
}
