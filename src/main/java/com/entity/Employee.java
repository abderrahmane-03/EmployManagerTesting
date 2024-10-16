package com.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Employee")
@DiscriminatorValue("Employee")
public class Employee extends User {

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations;

    public List<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
    }
}
