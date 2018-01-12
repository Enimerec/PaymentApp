package pl.sda.EmployeeUtils;

import pl.sda.DataUtils.DataUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyEmployee implements Payable {

    private BigDecimal salary;

    public MonthlyEmployee(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return DataUtils.isLastWorkingDayOfMonth(day);

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
