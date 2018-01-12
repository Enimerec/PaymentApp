package pl.sda.EmployeeUtils;

import org.junit.jupiter.api.BeforeEach;
import pl.sda.EmployeeUtils.HourlyEmployee;
import pl.sda.EmployeeUtils.WorkingDay;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HourlyEmployeeTest {

    private HourlyEmployee hourlyEmployeeTest;
    private WorkingDay workingDayTest;
    private HourlyEmployee hourlyEmployeeTest2;
    private HourlyEmployee hourlyEmployeeTest3;

    @BeforeEach
    public void setup(){
        hourlyEmployeeTest = new HourlyEmployee(new BigDecimal(1));
        hourlyEmployeeTest2 = new HourlyEmployee(new BigDecimal(1));
        hourlyEmployeeTest3 = new HourlyEmployee(new BigDecimal(1));

        workingDayTest = new WorkingDay(LocalDate.of(2018,1,10), 10);
        WorkingDay workingDayTest3 = new WorkingDay(LocalDate.of(2018,1,10).plusDays(2),9);
        WorkingDay workingDayTest2 = new WorkingDay(LocalDate.of(2018,1,10).plusDays(1), 8);

        hourlyEmployeeTest.addWorkingDays(workingDayTest);
        hourlyEmployeeTest.addWorkingDays(workingDayTest2);
        hourlyEmployeeTest3.addWorkingDays(workingDayTest3);
    }

    @org.junit.jupiter.api.Test
    void paymentForOneDayShouldBeCalculated(){
        LocalDate day = LocalDate.of(2018,1,12);
        assertTrue(hourlyEmployeeTest3.calculatePayment(day).compareTo(new BigDecimal("9.5")) == 0);
    }

    @org.junit.jupiter.api.Test
    void paymentShouldBeCalculated() {
        assertEquals(hourlyEmployeeTest.calculatePayment(LocalDate.of(2018,1,10).plusDays(2)),
                new BigDecimal("19.0"));
    }

    @org.junit.jupiter.api.Test
    void paymentShouldNotBeCaclutaedIfNotFriDay(){
        assertEquals(BigDecimal.ZERO,hourlyEmployeeTest.
                calculatePayment(LocalDate.of(2017,1,1)));
    }

    @org.junit.jupiter.api.Test
    void methodIsPaymentDayShouldReturnFalseifNoInFriday(){
        LocalDate day = LocalDate.of(2018,01,10);
        assertFalse(hourlyEmployeeTest.isPaymentDay(day));
    }
    @org.junit.jupiter.api.Test
    void shouldReturnTrueIfIsFriday(){
        LocalDate day = LocalDate.of(2018,01,12);
        assertTrue(hourlyEmployeeTest.isPaymentDay(day));
    }
    @org.junit.jupiter.api.Test
    void shouldReturn0IfNoWorkingDays(){
        LocalDate day = LocalDate.of(2018,01,12);
        assertEquals(BigDecimal.ZERO,hourlyEmployeeTest2.calculatePayment(day));
    }

}
