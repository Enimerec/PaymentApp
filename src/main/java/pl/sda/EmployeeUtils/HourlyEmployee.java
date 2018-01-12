package pl.sda.EmployeeUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HourlyEmployee implements Payable {

    private static final int WORKING_HOURS = 8;
    private static final BigDecimal OVERHOURS_RATE = new BigDecimal("1.5");

    private final BigDecimal hourlyRate;
    private List<WorkingDay> workingDays;

    public HourlyEmployee(BigDecimal hourlyRate){
        this.hourlyRate = hourlyRate;
        this.workingDays = new ArrayList<>();
    }

    public void addWorkingDays(WorkingDay workingDay){
        workingDays.add(workingDay);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return day.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if(isPaymentDay(day)){
            LocalDate monday = findMonday(day);
            List<WorkingDay>weekWorkingDays = findWorkingDays(monday,day);
            return calculatePayment(weekWorkingDays);

        }else{
            return BigDecimal.ZERO;
        }

    }
    private BigDecimal calculatePayment(WorkingDay workingDay){
        int hours = workingDay.getHours();

        if (hours > 8) {
            BigDecimal paymentOverTime;
            BigDecimal paymentStandardTime;
            BigDecimal payment = new BigDecimal(0);

            int overTime = hours - WORKING_HOURS;
            paymentOverTime = hourlyRate.multiply(OVERHOURS_RATE).multiply(new BigDecimal(overTime));
            paymentStandardTime = hourlyRate.multiply(new BigDecimal(WORKING_HOURS));

            return payment.add(paymentOverTime).add(paymentStandardTime);

        }
        else{
            return hourlyRate.multiply(new BigDecimal(WORKING_HOURS));
        }

    }

    private BigDecimal calculatePayment(List<WorkingDay> weekWorkingDays) {
        BigDecimal payment = new BigDecimal(0);

        for (WorkingDay workingDay: weekWorkingDays) {
            payment = payment.add(calculatePayment(workingDay));
        }
        return payment;


    }

    private List<WorkingDay> findWorkingDays(LocalDate monday, LocalDate day) {
        return  workingDays.stream().filter(workingDay -> workingDay.isBetween(monday, day)).
                collect(Collectors.toList());
    }

    private LocalDate findMonday(LocalDate day) {
        if(day.getDayOfWeek() != DayOfWeek.FRIDAY){
            throw new IllegalArgumentException("Metoda findMonday musi byc wywolana dla piatku");
        }
        return  day.minusDays(4);
    }
}
