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

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)

@WebServlet("/vacations/*")
public class VacationServlet extends HttpServlet {

    private VacationService vacationService = new VacationService();  // Service layer
    private EmailService emailService = new EmailService();
    private static final String UPLOAD_DIR = "static";
    private VacationDAO vacationDAO;

    @Override
    public void init() {
        vacationDAO = new VacationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/vacationList":
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
            case "/createVacation":
                createVacation(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createVacation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String reason = request.getParameter("reason");
            Part justificationDocument = request.getPart("justificationDocument");
            if (justificationDocument == null || justificationDocument.getSize() == 0) {
                throw new ServletException("justificationDocument is mandatory.");
            }

            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");

            Employee fakeEmployee = new Employee();
            fakeEmployee.setId(2);
            fakeEmployee.setEmail("abdrahmanhafidi03@gmail.com");

            Date startDate = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                startDate = dateFormat.parse(startDateString);
            } catch (ParseException e) {
                throw new ServletException("Invalid date format for startDateString.", e);
            }
            Date endDate = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                endDate = dateFormat.parse(endDateString);
            } catch (ParseException e) {
                throw new ServletException("Invalid date format for startDateString.", e);
            }
            String fileName = Paths.get(justificationDocument.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + File.separator + fileName;
            justificationDocument.write(filePath);

            Vacation vacation = new Vacation( startDate, endDate, reason,  VacationStatus.PENDING, new java.sql.Date(System.currentTimeMillis()),fileName, fakeEmployee);

            vacationDAO.saveVacation(vacation);

            emailService.sendEmail(fakeEmployee.getEmail(), "Vacation Request Submitted", "Your vacation request has been submitted for approval.");

            response.sendRedirect(request.getContextPath() + "/vacationList");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error creating vacation: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vacation> vacations = vacationDAO.getAllVacations();
        request.setAttribute("vacationList", vacations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/vacationList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources
    }
}
