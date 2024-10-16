package com.entity;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Role")
@Table(name = "\"User\"")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected String name;

    @Column(unique = true)
    protected String email;

    protected String phone;

    @Temporal(TemporalType.DATE)
    protected Date dob;

    @Column(unique = true)
    protected String ssn;

    protected String department;
    protected String position;

    @Temporal(TemporalType.DATE)
    protected Date hire_date;
    protected String picture;
    protected Double salary;
    protected int number_of_children;
    protected int vacation;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVacation() {
        return vacation;
    }

    public void setVacation(int vacation) {
        this.vacation = vacation;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getDob() {
        return dob;
    }

    public String getSsn() {
        return ssn;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public String getPicture() {
        return picture;
    }

    public Double getSalary() {
        return salary;
    }

    public int getNumber_of_children() {
        return number_of_children;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setNumber_of_children(int number_of_children) {
        this.number_of_children = number_of_children;
    }
}
