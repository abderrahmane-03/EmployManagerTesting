import com.DAO.imp.VacationDAO;
import com.entity.Vacation;
import com.enums.AbsenceStats;
import com.service.AbsenceReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbsenceReportServiceTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private VacationDAO vacationDAO;
    private AbsenceReportService absenceReportService;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("EmployeePU");
        entityManager = entityManagerFactory.createEntityManager();
        vacationDAO = new VacationDAO();
        absenceReportService = new AbsenceReportService();

        entityManager.getTransaction().begin();
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.parse(date);
    }

    @Test
    public void testGenerateMonthlyReport() throws ParseException {
        Vacation vacation1 = new Vacation();
        vacation1.setStartDate(parseDate("2024/01/01"));
        vacation1.setEndDate(parseDate("2024/01/10"));
        entityManager.persist(vacation1);
        entityManager.getTransaction().commit(); // Ensure the transaction is committed

        // Start a new transaction for report generation
        entityManager.getTransaction().begin();

        // Generate the report
        List<AbsenceStats> report = absenceReportService.generateMonthlyReport(parseDate("2024/01/01"), parseDate("2024/01/15"));

        // Validate the report
        assertNotNull(report, "The report should not be null");
        // Additional assertions based on the expected size or content of the report

        // Clean up the test data
        entityManager.createQuery("DELETE FROM Vacation").executeUpdate(); // Cleanup
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
