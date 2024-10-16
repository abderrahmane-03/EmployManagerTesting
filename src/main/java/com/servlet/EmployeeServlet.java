package com.servlet;

import com.DAO.imp.EmployeeDAO;
import com.DAO.inf.EmployeeDaoInterface;
import com.entity.Employee;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class EmployeeServlet extends HttpServlet {
    private EmployeeDaoInterface employeeDao;
    private static final String UPLOAD_DIR = "static";

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/search":
                search(request, response);
                break;
            case "/filter":
                filter(request, response);
                break;
            case "/update":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteEmployee(request, response);
                break;
            case "/home":
                listEmployees(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/create":
                createEmployee(request, response);
                break;
            case "/edit":
                updateEmployee(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        Employee employee = new Employee();
        populateEmployeeFromRequest(employee, request);
        employeeDao.saveEmployee(employee);
        response.sendRedirect("/EmployManger/home");
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeDao.getEmployeeById(id);

        populateEmployeeFromRequest(employee, request);

        // Update employee and redirect
        employeeDao.updateEmployee(employee);
        response.sendRedirect("/EmployManger/home");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDao.deleteEmployee(id);
        response.sendRedirect("/EmployManger/home");
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        List<Employee> employees = employeeDao.getAllEmployees();
        request.setAttribute("employeeList", employees);
        forwardToPage(request, response, "/views/index.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeDao.getEmployeeById(id);
        request.setAttribute("employee", employee);
        forwardToPage(request, response, "views/updateForm.jsp");
    }

    private void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        String filterOption = request.getParameter("filter");
        List<Employee> filteredEmployees = employeeDao.filter(filterOption);
        request.setAttribute("employeeList", filteredEmployees);
        forwardToPage(request, response, "/views/index.jsp");
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeDao = new EmployeeDAO();
        String searchTerm = request.getParameter("search");
        List<Employee> searchResults = employeeDao.search(searchTerm);
        request.setAttribute("employeeList", searchResults);
        forwardToPage(request, response, "/views/index.jsp");
    }

    private void populateEmployeeFromRequest(Employee employee, HttpServletRequest request) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String position = request.getParameter("position");
        String department = request.getParameter("department");
        String SSN = request.getParameter("ssn");
        int Vacation = Integer.parseInt(request.getParameter("vacation"));
        // Convert DOB (Date of Birth) to Date type
        String dobString = request.getParameter("dob");
        Date DOB = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DOB = dateFormat.parse(dobString);
        } catch (ParseException e) {
            throw new ServletException("Invalid date format for DOB.", e);
        }

        // Ensure picture part is mandatory
        Part filePart = request.getPart("picture");
        if (filePart == null || filePart.getSize() == 0) {
            throw new ServletException("Picture is mandatory.");
        }

        // Convert hireDate to Date type
        String hireDateString = request.getParameter("hire_date");
        Date hireDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            hireDate = dateFormat.parse(hireDateString);
        } catch (ParseException e) {
            throw new ServletException("Invalid date format for hireDate.", e);
        }

        // Convert salary and number of children to correct types
        Double salary = Double.parseDouble(request.getParameter("salary"));
        int numberOfChildren = Integer.parseInt(request.getParameter("number_of_children"));

        // Handling file uploads (picture)
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Populate the Employee entity
        employee.setName(name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setPosition(position);
        employee.setPicture(fileName);
        employee.setDob(DOB);
        employee.setSsn(SSN);
        employee.setSalary(salary);
        employee.setDepartment(department);
        employee.setHire_date(hireDate);
        employee.setNumber_of_children(numberOfChildren);
        employee.setVacation(Vacation);
    }


    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
