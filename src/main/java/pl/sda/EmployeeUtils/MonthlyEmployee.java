package pl.sda.EmployeeUtils;

import java.math.BigDecimal;
import java.security.acl.LastOwnerException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MonthlyEmployee implements Payable {

    private final static Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add( DayOfWeek.SUNDAY);
    }

    private BigDecimal salary;

    public MonthlyEmployee(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        if(FREE_DAYS.contains(day.getDayOfWeek()) ){
            return false;

        }else if(day.getDayOfWeek() == DayOfWeek.FRIDAY){
            return day.getDayOfMonth() >= day.lengthOfMonth()-2;
        }
        else {
            return day.getDayOfMonth()== day.lengthOfMonth();
        }
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if(isPaymentDay(day)){
            return salary;
        }
        else{
            return BigDecimal.ZERO;
        }
    }
}
