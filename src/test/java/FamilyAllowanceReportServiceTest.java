
import com.DAO.inf.EmployeeDaoInterface;
import com.entity.Employee;
import com.service.FamilyAllowanceReportService;
import com.service.FamilyAllowanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FamilyAllowanceReportServiceTest {

    @Mock
    private EmployeeDaoInterface employeeDAO;

    @Mock
    private FamilyAllowanceService familyAllowanceService;

    @InjectMocks
    private FamilyAllowanceReportService familyAllowanceReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testCalculateTotalAllowance() {
        // Arrange
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(familyAllowanceService.calculateAllowance(employee1)).thenReturn(300.0);
        when(familyAllowanceService.calculateAllowance(employee2)).thenReturn(150.0);

        // Act
        double totalAllowance = familyAllowanceReportService.calculateTotalAllowance(employees);

        // Assert
        assertEquals(450.0, totalAllowance, "The total allowance should be the sum of individual allowances");
        verify(familyAllowanceService, times(1)).calculateAllowance(employee1);
        verify(familyAllowanceService, times(1)).calculateAllowance(employee2);
    }

    @Test
    public void testGetEmployeesWithAllowances() {
        // Arrange
        Date startDate = Date.valueOf("2024-01-01");
        Date endDate = Date.valueOf("2024-12-31");
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

        when(employeeDAO.getEmployeesWithAllowancesBetween(startDate, endDate)).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = familyAllowanceReportService.getEmployeesWithAllowances(startDate, endDate);

        // Assert
        assertNotNull(actualEmployees);
        assertEquals(2, actualEmployees.size(), "The list should contain 2 employees");
        verify(employeeDAO, times(1)).getEmployeesWithAllowancesBetween(startDate, endDate);
    }
}
