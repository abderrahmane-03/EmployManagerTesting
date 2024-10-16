package com.DAO.inf;

import com.entity.Vacation;

import java.util.List;

public interface VacationDaoInterface {

        public List<Vacation> getAllVacations();
        public void saveVacation(Vacation vacation);
        public void deleteVacation(int vacationId);
        public void updateVacation(Vacation vacation);
        public Vacation getVacationById(int id);
        public void close() ;

}
