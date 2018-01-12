package pl.sda.EmployeeUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CommisionEmployee implements Payable {

    private List<Bill> billList ;
    private BigDecimal twoWeekSalary;
    private BigDecimal commission;

    private final static Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add( DayOfWeek.SUNDAY);
    }

    public CommisionEmployee(BigDecimal twoWeekSalary, BigDecimal commission) {
        this.twoWeekSalary = twoWeekSalary;
        this.billList = new ArrayList<>();
        this.commission = commission;
    }


    public void addBill(Bill bill){
        billList.add(bill);
    }
    private LocalDate findSecondFridayOfTheYear(int year){
        LocalDate firstDayOfTheYear = LocalDate.of(year,1,1);
        while (!(firstDayOfTheYear.getDayOfWeek().equals(DayOfWeek.FRIDAY))){
            firstDayOfTheYear = firstDayOfTheYear.plusDays(1);
        }

        return firstDayOfTheYear.plusDays(7);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return isSecondFriday(day) || isLastWorkingDayOfTheYear(day);

    }

    private boolean isLastWorkingDayOfTheYear(LocalDate day) {
        LocalDate lastDay = LocalDate.of(day.getYear(), 12, day.lengthOfMonth());
        while (FREE_DAYS.contains(lastDay.getDayOfWeek())) {
            lastDay = lastDay.minusDays(1);
        }
        return day.equals(lastDay);

    }

    private boolean isSecondFriday(LocalDate day) {
        LocalDate secondFridayOfTheYear = findSecondFridayOfTheYear(day.getYear());
        int days = day.getDayOfYear() - secondFridayOfTheYear.getDayOfYear();

        return days % 14 == 0;
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if(isPaymentDay(day)){
            LocalDate from = findMachingFirstDay(day);
            List<Bill>billsToPayFor = findbillList(from,day);
            int workingDays = countWorkingDays(from,day);

            return calculatePayment(billsToPayFor,workingDays);
        }
        else{
            return BigDecimal.ZERO;
        }

    }

    private BigDecimal calculatePayment(List<Bill>billList,int workingDays){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal oneDaySalary = twoWeekSalary.divide(new BigDecimal(10));

        for (Bill b : billList) {
            sum = sum.add(b.getValue());
        }

        return  oneDaySalary.multiply(new BigDecimal(workingDays)).
                add(sum.multiply(commission));
    }


    private int countWorkingDays(LocalDate fromDate,LocalDate toDate){
        int workingDays = 0;
        do{
            if(!(FREE_DAYS.contains(fromDate.getDayOfWeek()))){
                workingDays++;
            }
            fromDate = fromDate.plusDays(1);
        }
        while (fromDate.isBefore(toDate)|| fromDate.isEqual(toDate));

        return workingDays;
    }
    private LocalDate findMachingFirstDay(LocalDate day) {
        if(day.isEqual(findSecondFridayOfTheYear(day.getYear()))){
            return LocalDate.of(day.getYear(),1,1);
        }
        else if(isLastWorkingDayOfTheYear(day)){
            while (!(isSecondFriday(day))){
                day = day.minusDays(1);
            }
            return day.plusDays(3);
        }
        else{
            return day.minusDays(11);
        }
    }

    public List<Bill>findbillList(LocalDate from,LocalDate to){
        return  billList.stream().filter(bill -> bill.isBetween(from, to)).
                collect(Collectors.toList());
    }
}
