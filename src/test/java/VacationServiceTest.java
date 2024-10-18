
import com.DAO.imp.EmployeeDAO;
import com.DAO.imp.VacationDAO;
import com.entity.Employee;
import com.entity.Vacation;
import com.enums.VacationStatus;
import com.service.VacationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VacationServiceTest {

    @Mock
    private VacationDAO vacationDAO;

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private VacationService vacationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateVacationStatus_Success() throws Exception {

        Employee employee = new Employee();
        employee.setVacation(10);

        Vacation vacation = new Vacation();
        vacation.setId(1);
        vacation.setEmployee(employee);
        vacation.setStartDate(Date.from(LocalDate.of(2024, 10, 15)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        vacation.setEndDate(Date.from(LocalDate.of(2024, 10, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        VacationStatus newStatus = VacationStatus.APPROVED;


        vacationService.updateVacationStatus(vacation, newStatus);


        assertEquals(VacationStatus.APPROVED, vacation.getStatus());
        assertEquals(5, vacation.getEmployee().getVacation()); // 5 days left


        verify(vacationDAO, times(1)).updateVacation(vacation);
        verify(employeeDAO, times(1)).updateEmployee(employee);
    }

    @Test
    public void testUpdateVacationStatus_InsufficientVacationDays() {

        Employee employee = new Employee();
        employee.setVacation(2);

        Vacation vacation = new Vacation();
        vacation.setEmployee(employee);
        vacation.setStartDate(Date.from(LocalDate.of(2024, 10, 15)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        vacation.setEndDate(Date.from(LocalDate.of(2024, 10, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        VacationStatus newStatus = VacationStatus.APPROVED;


        Exception exception = assertThrows(Exception.class, () ->
                vacationService.updateVacationStatus(vacation, newStatus)
        );

        assertEquals("The employee does not have enough vacation days.", exception.getMessage());


        verify(vacationDAO, never()).updateVacation(any());
        verify(employeeDAO, never()).updateEmployee(any());
    }

    @Test
    public void testGetVacationById() {

        Vacation vacation = new Vacation();
        vacation.setId(1);

        when(vacationDAO.getVacationById(1)).thenReturn(vacation);


        Vacation result = vacationService.getVacationById(1);


        assertEquals(1, result.getId());
        verify(vacationDAO, times(1)).getVacationById(1);
    }
}
