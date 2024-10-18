import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.service.FamilyAllowanceService;

public class FamilyAllowanceServiceTest {

    private FamilyAllowanceService familyAllowanceService;
    private Employee mockEmployee;

    @BeforeEach
    public void setUp() {
        familyAllowanceService = new FamilyAllowanceService();
        mockEmployee = mock(Employee.class); // Create a mock Employee object
    }

    @Test
    public void testCalculateAllowance_LessThan6000() {
        when(mockEmployee.getSalary()).thenReturn(5500.0); // Mock salary
        when(mockEmployee.getNumber_of_children()).thenReturn(4); // Mock number of children

        double allowance = familyAllowanceService.calculateAllowance(mockEmployee);

        assertEquals(1050, allowance); // 3 * 300 + 1 * 150
    }

    @Test
    public void testCalculateAllowance_GreaterThan8000() {
        when(mockEmployee.getSalary()).thenReturn(8500.0); // Mock salary
        when(mockEmployee.getNumber_of_children()).thenReturn(5); // Mock number of children

        double allowance = familyAllowanceService.calculateAllowance(mockEmployee);

        assertEquals(820, allowance);
    }

    @Test
    public void testCalculateAllowance_NoChildren() {
        when(mockEmployee.getSalary()).thenReturn(9000.0);
        when(mockEmployee.getNumber_of_children()).thenReturn(0);

        double allowance = familyAllowanceService.calculateAllowance(mockEmployee);

        assertEquals(0, allowance); // No allowance for 0 children
    }
}
