package pl.sda.EmployeeUtils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommisionEmployeeTest {

    @Test
    void shouldCalculatePaymantFromTheEndOfTheYear(){
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(100),
                new BigDecimal(0.25));
        //when
        Bill billEndOfTheYear = new Bill(LocalDate.of(2018,12,31),
                new BigDecimal(4));
        commisionEmployee.addBill(billEndOfTheYear);
        BigDecimal actualPayment = commisionEmployee.calculatePayment(
                LocalDate.of(2018,12,31));
        //then
        assertTrue(actualPayment.
                compareTo(new BigDecimal("11"))== 0);
    }


    @Test
    void calculatePaymentShouldReturnPayment(){
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(1),
                new BigDecimal(0.25));
        Bill bill = new Bill(LocalDate.of(2018,1,12),new BigDecimal(4));
        Bill bill2 = new Bill(LocalDate.of(2018,1,11),new BigDecimal(4));

        Bill billPreviusYear = new Bill(LocalDate.of(2017,12,28),new BigDecimal(4));
        Bill billNextWeek = new Bill(LocalDate.of(2018,5,15),new BigDecimal(4));


        //when
        commisionEmployee.addBill(bill);
        commisionEmployee.addBill(bill2);
        commisionEmployee.addBill(billPreviusYear);
        commisionEmployee.addBill(billNextWeek);

        //then
        assertTrue(commisionEmployee.calculatePayment(LocalDate.of(2018,1,12)).
                compareTo(new BigDecimal("3"))== 0);
        assertTrue(commisionEmployee.calculatePayment(LocalDate.of(2018,5,18)).
               compareTo(new BigDecimal("2"))== 0);


    }
    @Test
    void isPaymentDayShouldReturnFalseIfIsNot2Friday() {
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(1),
                new BigDecimal(0.25));
        //when

        //then
        assertFalse(commisionEmployee.isPaymentDay(LocalDate.of(2018,1,11)));
    }

    @Test
    void isPaymentDayShouldReturnTrueIfIs2Friday() {
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(1),
                new BigDecimal(0.25));
        //when

        //then
        assertTrue(commisionEmployee.isPaymentDay(LocalDate.of(2018,1,12)));
        assertTrue(commisionEmployee.isPaymentDay(LocalDate.of(2018,1,26)));
        assertTrue(commisionEmployee.isPaymentDay(LocalDate.of(2018,2,9)));
    }

    @Test
    void isPaymentDayShouldReturnFalseFridayButNot2Friday(){
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(1),
                new BigDecimal(0.25));
        //when

        //then
        assertFalse(commisionEmployee.isPaymentDay(LocalDate.of(2018,1,5)));
    }

    @Test
    void ShouldReturnTrueForLastWorkingDay(){
        //given
        CommisionEmployee commisionEmployee = new CommisionEmployee(new BigDecimal(1),
                new BigDecimal(0.25));
        //when

        //then
        assertTrue(commisionEmployee.isPaymentDay(LocalDate.of(2018,12,31)));
    }
}
