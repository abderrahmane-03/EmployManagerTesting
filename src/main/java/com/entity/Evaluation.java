package com.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;  // Assuming Employee is used for both employees and managers

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private String status; // E.g., "Pending", "Completed"

    private String comments;

    public Evaluation() {
    }

    public Evaluation(Employee employee, Employee manager, Date dueDate, String status, String comments) {
        this.employee = employee;
        this.manager = manager;
        this.dueDate = dueDate;
        this.status = status;
        this.comments = comments;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
