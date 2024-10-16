package com.servlet;

import com.entity.Employee;
import com.service.FamilyAllowanceService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee/family-allowance-summary")
public class EmployeeFamilyAllowanceServlet extends HttpServlet {

    private FamilyAllowanceService allowanceService;

    @Override
    public void init() {
        allowanceService = new FamilyAllowanceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assume employee is logged in and we have access to their session
        Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");

        if (employee != null) {
            double allowance = allowanceService.calculateAllowance(employee);
            request.setAttribute("allowance", allowance);
        } else {
            request.setAttribute("error", "Employee not found.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/employeeFamilyAllowanceSummary.jsp");
        dispatcher.forward(request, response);
    }
}
