package com.servlet;

import com.DAO.imp.RecruiterDAO;
import com.DAO.inf.RecruiterDaoInterface;
import com.entity.Application;
import com.entity.JobOffer;
import com.entity.Recruiter;
import com.enums.ApplicationStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/recruiters/*")
public class RecruiterServlet extends HttpServlet {

    private RecruiterDaoInterface recruiterDao;

    @Override
    public void init() {
        recruiterDao = new RecruiterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/new":
                showNewRecruiterForm(request, response);
                break;
            case "/edit":
                showEditRecruiterForm(request, response);
                break;
            case "/delete":
                deleteRecruiter(request, response);
                break;
            case "/filterApplicationByStatus":
                filterApplicationByStatus(request, response);
                break;
            case "/filterApplicationBySkill":
                filterApplicationBySkill(request, response);
                break;
            case "/":
            case "":
            default:
                listRecruiters(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/insert":
                insertRecruiter(request, response);
                break;
            case "/update":
                updateRecruiter(request, response);
                break;
            case "/acceptApplication":
                acceptApplication(request, response);
                break;
            case "/rejectApplication":
                rejectApplication(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
                break;
        }
    }


    private void listRecruiters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Recruiter> recruiters = recruiterDao.getAllRecruiters();
        request.setAttribute("recruiterList", recruiters);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/recruiterList.jsp");
        dispatcher.forward(request, response);
    }


    private void showNewRecruiterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/recruiterForm.jsp");
        dispatcher.forward(request, response);
    }

    // Method to show the form for editing an existing recruiter
    private void showEditRecruiterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Recruiter recruiter = recruiterDao.getRecruiterById(id);
        if (recruiter != null) {
            request.setAttribute("recruiter", recruiter);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/recruiterForm.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recruiter not found");
        }
    }

    // Method to insert a new recruiter
    private void insertRecruiter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Recruiter recruiter = new Recruiter();
        recruiter.setName(name);
        recruiter.setEmail(email);

        recruiterDao.saveRecruiter(recruiter);
        response.sendRedirect(request.getContextPath() + "/recruiters");
    }

    // Method to update an existing recruiter
    private void updateRecruiter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Recruiter recruiter = recruiterDao.getRecruiterById(id);
        if (recruiter != null) {
            recruiter.setName(name);
            recruiter.setEmail(email);
            recruiterDao.updateRecruiter(recruiter);
            response.sendRedirect(request.getContextPath() + "/recruiters");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recruiter not found");
        }
    }

    // Method to delete a recruiter
    private void deleteRecruiter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        recruiterDao.deleteRecruiter(id);
        response.sendRedirect(request.getContextPath() + "/recruiters");
    }
    private void createJobOffer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("jobName");
        String requiredSkills = request.getParameter("requiredSkills");
        int recruiterId = Integer.parseInt(request.getParameter("recruiterId")); // Assuming recruiter ID is passed in the form

        Recruiter recruiter = recruiterDao.getRecruiterById(recruiterId);
        if (recruiter != null) {
            JobOffer jobOffer = new JobOffer();
            jobOffer.setName(name);
            jobOffer.setRequiredSkills(requiredSkills);
            jobOffer.setRecruiter(recruiter);

            recruiterDao.saveJobOffer(jobOffer);
            response.sendRedirect(request.getContextPath() + "/recruiters"); // Redirect to the list of recruiters or job offers
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recruiter not found");
        }
    }

    private void acceptApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        Application application = recruiterDao.getApplicationById(applicationId);

        if (application != null) {
            application.setStatus(ApplicationStatus.ACCEPTED);
            recruiterDao.updateApplication(application);
            response.sendRedirect(request.getContextPath() + "/recruiters/applications");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Application not found");
        }
    }

    private void rejectApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        Application application = recruiterDao.getApplicationById(applicationId);

        if (application != null) {
            application.setStatus(ApplicationStatus.REJECTED);
            recruiterDao.updateApplication(application);
            response.sendRedirect(request.getContextPath() + "/recruiters/applications");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Application not found");
        }
    }
    private void filterApplicationByStatus(HttpServletRequest request, HttpServletResponse response){
        String status = request.getParameter("status");
        recruiterDao.filterApplicationByStatus(status);

    }

    private void filterApplicationBySkill(HttpServletRequest request, HttpServletResponse response){
        String skill = request.getParameter("skill");
        recruiterDao.filterApplicationBySkill(skill);
    }

    @Override
    public void destroy() {
        recruiterDao.close();
    }
}
