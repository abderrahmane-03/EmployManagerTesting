import com.DAO.imp.EvaluationDAO;
import com.entity.Evaluation;
import com.entity.Employee;
import com.entity.Recruiter;
import com.service.EmailService;
import com.service.ScheduledTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScheduledTaskServiceTest {

    @Mock
    private EvaluationDAO evaluationDAO;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ScheduledTaskService scheduledTaskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEvaluationReminder() {
        // Arrange
        Employee employee = new Employee();
        employee.setName("John Doe");

        Recruiter manager = new Recruiter();
        manager.setEmail("manager@example.com");  // Correctly set manager email

        Evaluation evaluation = new Evaluation();
        evaluation.setEmployee(employee);
        evaluation.setManager(manager); // Set the manager to the Recruiter, not the employee

        LocalDate dueDate = LocalDate.now().plusDays(7);
        Date dueDateAsDate = Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        evaluation.setDueDate(dueDateAsDate);

        List<Evaluation> evaluations = Arrays.asList(evaluation);
        when(evaluationDAO.getPendingEvaluationsWithin(7)).thenReturn(evaluations);

        // Act
        scheduledTaskService.startScheduledTask();
        scheduledTaskService.sendEvaluationReminder();

        // Assert
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService, times(1)).sendEmail(emailCaptor.capture(), anyString(), anyString());

        String capturedEmail = emailCaptor.getValue();
        assertEquals("manager@example.com", capturedEmail);  // Check if the captured email is correct
    }


    @Test
    void testCalculateInitialDelayForNoon() {
        // Act
        long delay = scheduledTaskService.calculateInitialDelayForNoon();

        // Assert that the delay is reasonable (non-negative)
        assertEquals(true, delay >= 0);
    }
}
