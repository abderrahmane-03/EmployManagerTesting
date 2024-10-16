package com.DAO.imp;

import com.DAO.inf.EmployeeDaoInterface;
import com.entity.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

public class EmployeeDAO implements EmployeeDaoInterface {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EmployeeDAO() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("EmployeePU");  // Use the name of your persistence unit
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("EntityManagerFactory created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create EntityManagerFactory.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        try {
            entityManager.getTransaction().begin();
            employees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            entityManager.getTransaction().commit();

            if (employees != null && !employees.isEmpty()) {
                System.out.println("Employees fetched: " + employees.size());
            } else {
                System.out.println("No employees found in the database.");
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);  // merge handles both new and existing entities
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;  // Re-throw the exception to handle it in higher layers
        }
    }


    @Override
    public void deleteEmployee(int employeeId) {
        try {
            entityManager.getTransaction().begin();
            Employee employee = entityManager.find(Employee.class, employeeId);
            if (employee != null) {
                entityManager.remove(employee);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(employee);  // merge for updates
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = null;
        try {
            employee = entityManager.find(Employee.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> filter(String filterOption) {
        List<Employee> employees = null;
        try {
            employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.department = :filterOption", Employee.class)
                    .setParameter("filterOption", filterOption)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> search(String searchTerm) {
        List<Employee> employees = null;
        try {
            employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.name LIKE :searchTerm", Employee.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> getEmployeesWithAllowancesBetween(Date startDate, Date endDate) {
        String queryStr = "SELECT e FROM Employee e WHERE e.hireDate BETWEEN :startDate AND :endDate";
        TypedQuery<Employee> query = entityManager.createQuery(queryStr, Employee.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
