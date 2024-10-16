package com.service;

import com.DAO.inf.EmployeeDaoInterface;
import com.entity.Employee;

import java.sql.Date;
import java.util.List;

public class FamilyAllowanceReportService {

    private EmployeeDaoInterface employeeDAO;
    private FamilyAllowanceService familyAllowanceService;

    public FamilyAllowanceReportService(EmployeeDaoInterface employeeDAO, FamilyAllowanceService familyAllowanceService) {
        this.employeeDAO = employeeDAO;
        this.familyAllowanceService = familyAllowanceService;
    }

    // Calculates the total family allowance for a list of employees
    public double calculateTotalAllowance(List<Employee> employees) {
        double total = 0;
        for (Employee employee : employees) {
            total += familyAllowanceService.calculateAllowance(employee);
        }
        return total;
    }

    // Fetches employees whose family allowances were calculated between the given date range
    public List<Employee> getEmployeesWithAllowances(Date startDate, Date endDate) {
        // Fetch employees based on date range (this would require a query implementation in DAO)
        return employeeDAO.getEmployeesWithAllowancesBetween(startDate, endDate);
    }
}
