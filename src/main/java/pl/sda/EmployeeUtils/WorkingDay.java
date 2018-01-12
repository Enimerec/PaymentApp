package pl.sda.EmployeeUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class WorkingDay {

    final private LocalDate date;
    final private int hours;

    public boolean isBetween(LocalDate fromDate, LocalDate toDate) {
        if (date.isEqual(fromDate) || date.isEqual(toDate) ||
                (date.isAfter(fromDate) && date.isBefore(toDate))) {
            return true;
        } else {
            return false;
        }

    }
}
