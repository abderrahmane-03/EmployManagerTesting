package com.servlet;

import com.DAO.imp.EmployeeDAO;
import com.entity.Employee;
import com.service.FamilyAllowanceService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/calculate-family-allowance")
public class CalculateFamilyAllowanceServlet extends HttpServlet {

    private FamilyAllowanceService allowanceService;
    EmployeeDAO employeeDAO = new EmployeeDAO();
    @Override
    public void init() {
        allowanceService = new FamilyAllowanceService();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get employee ID from request parameter
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        // Fetch employee from database (assuming you have an EmployeeService or DAO to fetch Employee)
        Employee employee = employeeDAO.getEmployeeById(employeeId);

        if (employee != null) {
            double allowance = allowanceService.calculateAllowance(employee);
            request.setAttribute("allowance", allowance);
            request.setAttribute("employee", employee);
        } else {
            request.setAttribute("error", "Employee not found.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/familyAllowanceResult.jsp");
        dispatcher.forward(request, response);
    }
}
