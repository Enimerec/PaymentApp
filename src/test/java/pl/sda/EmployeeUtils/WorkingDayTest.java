package pl.sda.EmployeeUtils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkingDayTest {

    @Test
    void isBetweenShouldReturnTrueIfDateIsBetween() {
        ///given
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate tomorrow = now.plusDays(1);

        //when
        WorkingDay workingDay = new WorkingDay(now,8);
        WorkingDay workingDay2 = new WorkingDay(yesterday,8);
        WorkingDay workingDay3 = new WorkingDay(tomorrow,8);

        //then
        assertTrue(workingDay.isBetween(yesterday,tomorrow));
        assertTrue(workingDay2.isBetween(yesterday,tomorrow));
        assertTrue(workingDay3.isBetween(yesterday,tomorrow));

    }

    @Test
    void isBetweenShouldReturnFalseIfDateIsNotBetween(){
        //given
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate tomorrow = now.plusDays(1);

        //when
        WorkingDay workingDay = new WorkingDay(yesterday,8);

        //then
        assertFalse(workingDay.isBetween(now,tomorrow));

    }

}