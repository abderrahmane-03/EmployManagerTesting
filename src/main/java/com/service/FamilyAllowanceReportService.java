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

    public double calculateTotalAllowance(List<Employee> employees) {
        double total = 0;
        for (Employee employee : employees) {
            total += familyAllowanceService.calculateAllowance(employee);
        }
        return total;
    }

    public List<Employee> getEmployeesWithAllowances(Date startDate, Date endDate) {
        return employeeDAO.getEmployeesWithAllowancesBetween(startDate, endDate);
    }
}
