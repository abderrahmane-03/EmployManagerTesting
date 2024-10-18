package com.service;

import com.DAO.imp.VacationDAO;
import com.entity.Vacation;
import com.enums.AbsenceStats;

import java.util.Date;
import java.util.List;

import com.DAO.imp.VacationDAO;
import com.entity.Vacation;
import com.enums.AbsenceStats;

import java.util.Date;
import java.util.List;

public class AbsenceReportService {
    private VacationDAO vacationDAO = new VacationDAO();

    public List<AbsenceStats> generateMonthlyReport(Date startDate, Date endDate) {
        List<Vacation> vacations = vacationDAO.getVacationsBetween(startDate, endDate);
        System.out.println("Vacations retrieved: " + vacations.size());
        return aggregateVacations(vacations);
    }


    private List<AbsenceStats> aggregateVacations(List<Vacation> vacations) {
        return null;
    }
}

