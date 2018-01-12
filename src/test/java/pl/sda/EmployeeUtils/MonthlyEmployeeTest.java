package pl.sda.EmployeeUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.asserts.Assertion;
import pl.sda.EmployeeUtils.MonthlyEmployee;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MonthlyEmployeeTest {

    private static final  MonthlyEmployee monthlyEmployee = new MonthlyEmployee(new BigDecimal(100));

    @Test
    void shouldReturnZeroForNotPaymentDay(){
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2018,1,25))
                .compareTo(BigDecimal.ZERO)== 0);
    }

    @Test
    void shouldReturnPayMentForLastPaymentDay(){
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2018,1,31))
                .compareTo(new BigDecimal(100))== 0);
    }

    @Test
    void shouldReturnTrueForLastWorkingDayOfMonth() {
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 1, 31)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 30)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 29)));
    }

    @Test
    void shouldReturnFalseForSaturdayAndSunday() {
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 31)));
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 31)));
    }

    @Test
    void shouldReturnFalseForNonLastWorkingDay() {
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 4, 27)));
    }
}
