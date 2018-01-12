package pl.sda.EmployeeUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public interface Payable {

    boolean isPaymentDay(LocalDate day);
    BigDecimal calculatePayment(LocalDate day);

}
