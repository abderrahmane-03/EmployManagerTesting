package com.servlet;

import com.DAO.imp.VacationDAO;
import com.entity.Employee;
import com.entity.User;
import com.entity.Vacation;
import com.enums.VacationStatus;
import com.service.EmailService;
import com.service.VacationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;

public class AdminServlet extends HttpServlet {

    private VacationService vacationService = new VacationService();  // Service layer
    private EmailService emailService = new EmailService();
    private static final String UPLOAD_DIR = "static";
    private VacationDAO vacationDAO;

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/Reject":
                try {
                    Reject(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/Approve":
                try {
                    Approve(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/vacationListAdmin":
                listVacations(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {


            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void Approve(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int vacationId = Integer.parseInt(request.getParameter("id"));
        VacationStatus newStatus = VacationStatus.APPROVED;

        Vacation vacation = vacationService.getVacationById(vacationId);
        vacationService.updateVacationStatus(vacation, newStatus);

        // Notify the employee about the vacation status update
        String subject = "Your Vacation Request is " + newStatus;
        String message = "Your vacation request for " + vacation.getStartDate() + " to " + vacation.getEndDate() + " has been " + newStatus;
        emailService.sendEmail(vacation.getEmployee().getEmail(), subject, message);

        response.sendRedirect(request.getContextPath() + "/vacationListAdmin");
    }
    private void Reject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int vacationId = Integer.parseInt(request.getParameter("id"));
        VacationStatus newStatus = VacationStatus.REJECTED;

        Vacation vacation = vacationService.getVacationById(vacationId);
        vacationService.updateVacationStatus(vacation, newStatus);

        // Notify the employee about the vacation status update
        String subject = "Your Vacation Request is " + newStatus;
        String message = "Your vacation request for " + vacation.getStartDate() + " to " + vacation.getEndDate() + " has been " + newStatus;
        emailService.sendEmail(vacation.getEmployee().getEmail(), subject, message);

        response.sendRedirect(request.getContextPath() + "/vacationListAdmin");
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        vacationDAO = new VacationDAO();
        List<Vacation> vacations = vacationDAO.getAllVacations();
        System.out.println(vacations);

        request.setAttribute("vacationList", vacations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/vacationListAdmin.jsp");
        dispatcher.forward(request, response);
    }

}
