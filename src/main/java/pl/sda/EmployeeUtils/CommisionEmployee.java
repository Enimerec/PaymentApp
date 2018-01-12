package pl.sda.EmployeeUtils;

import pl.sda.DataUtils.DataUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CommisionEmployee implements Payable {

    private List<Bill> billList ;
    private BigDecimal twoWeekSalary;
    private BigDecimal commission;


    public CommisionEmployee(BigDecimal twoWeekSalary, BigDecimal commission) {
        this.twoWeekSalary = twoWeekSalary;
        this.billList = new ArrayList<>();
        this.commission = commission;
    }


    public void addBill(Bill bill){
        billList.add(bill);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return DataUtils.isSecondFriday(day,DataUtils.findSecondFridayOfTheYear(day.getYear())) ||
                DataUtils.isLastWorkingDayOfYear(day);


    }


    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if(isPaymentDay(day)){
            LocalDate from = findMachingFirstDay(day);
            List<Bill>billsToPayFor = findbillList(from,day);
            int workingDays = DataUtils.countWorkingDays(from,day);

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

    private LocalDate findMachingFirstDay(LocalDate day) {
        if(day.isEqual(DataUtils.findSecondFridayOfTheYear(day.getYear()))){
            return LocalDate.of(day.getYear(),1,1);
        }
        else if(DataUtils.isLastWorkingDayOfYear(day)){
            while (!(DataUtils.isSecondFriday(day,DataUtils.findSecondFridayOfTheYear(day.getYear())))){
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
