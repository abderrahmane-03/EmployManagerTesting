package com.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String requiredSkills;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;
    @ManyToOne
    @JoinColumn(name = "admin_id") // Define the foreign key column
    private Admin admin;
    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL)
    private List<Application> applications;

    protected JobOffer(int id,String name,String requiredSkills,Recruiter recruiter){
        this.id = id;
        this.name = name;
        this.requiredSkills = requiredSkills;
        this.recruiter = recruiter;
    }

    public JobOffer() {

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
    public String getRequiredSkills() {
        return requiredSkills;
    }
    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
    public Recruiter getRecruiter() {
        return recruiter;
    }
    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
    public List<Application> getApplications() {
        return applications;
    }
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
