package com.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"Recruiter\"")
public class Recruiter extends User {

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private List<JobOffer> jobOffers;

    // Getter and Setter for 'numberOfChildren'
    public int getNumberOfChildren() {
        return number_of_children;
    }

    public void setNumberOfChildren(int number_of_children) {
        this.number_of_children = number_of_children;
    }

    public List<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(List<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }
}
