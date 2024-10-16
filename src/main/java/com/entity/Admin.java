package com.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity

public class Admin extends User {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<JobOffer> jobOffers;

    public Admin() {
        super();
    }

    public List<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(List<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }
}