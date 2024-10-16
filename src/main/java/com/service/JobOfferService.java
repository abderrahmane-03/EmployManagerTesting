package com.service;

import com.entity.Employee;
import com.entity.JobOffer;
import java.util.List;
import com.DAO.imp.EmployeeDAO;

public class JobOfferService {
    EmployeeDAO employeeDAO;
    EmailService emailService;
    public void notifyEmployeesOnJobUpdate(JobOffer jobOffer) {
        List<Employee> employees = employeeDAO.getAllEmployees(); // Fetch relevant employees
        String subject = "New Job Offer: " + jobOffer.getName();
        String message = "A new job offer is available: " + jobOffer.getRequiredSkills();

        for (Employee employee : employees) {
            emailService.sendEmail(employee.getEmail(), subject, message);
        }
    }

}
