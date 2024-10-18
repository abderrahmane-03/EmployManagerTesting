import com.DAO.imp.EmployeeDAO;
import com.entity.Employee;
import com.entity.JobOffer;
import com.service.EmailService;
import com.service.JobOfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class JobOfferServiceTest {

    @InjectMocks
    private JobOfferService jobOfferService;

    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNotifyEmployeesOnJobUpdate() {
        // Mock Employee List
        Employee employee1 = mock(Employee.class);
        Employee employee2 = mock(Employee.class);
        when(employee1.getEmail()).thenReturn("employee1@example.com");
        when(employee2.getEmail()).thenReturn("employee2@example.com");

        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
        when(employeeDAO.getAllEmployees()).thenReturn(mockEmployees); // Mock EmployeeDAO response

        // Mock JobOffer
        JobOffer mockJobOffer = mock(JobOffer.class);
        when(mockJobOffer.getName()).thenReturn("Software Developer");
        when(mockJobOffer.getRequiredSkills()).thenReturn("Java, Spring Boot");

        // Call the method
        jobOfferService.notifyEmployeesOnJobUpdate(mockJobOffer);

        // Verify that sendEmail is called for each employee
        verify(emailService).sendEmail("employee1@example.com", "New Job Offer: Software Developer", "A new job offer is available: Java, Spring Boot");
        verify(emailService).sendEmail("employee2@example.com", "New Job Offer: Software Developer", "A new job offer is available: Java, Spring Boot");

        // Verify getAllEmployees was called once
        verify(employeeDAO, times(1)).getAllEmployees();
    }
}
