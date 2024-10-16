package com.entity;

import com.enums.ApplicationStatus;

import javax.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String resume;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  // Assuming enum type status

    @ManyToOne
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    protected Application(int id,String name,String email,String resume,ApplicationStatus status,JobOffer jobOffer){
        this.id = id;
        this.name = name;
        this.email = email;
        this.resume = resume;
        this.status = status;
        this.jobOffer = jobOffer;
    }

    public Application() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getResume() {
        return resume;
    }
    public void setResume(String resume) {
        this.resume = resume;
    }
    public ApplicationStatus getStatus() {
        return status;
    }
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    public JobOffer getJobOffer() {
        return jobOffer;
    }
    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

}