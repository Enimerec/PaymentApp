package pl.sda.DataUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class DataUtils {

    private final static Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add( DayOfWeek.SUNDAY);
    }

    public static LocalDate findSecondFridayOfTheYear(int year){
        LocalDate firstDayOfTheYear = LocalDate.of(year,1,1);
        while (!(firstDayOfTheYear.getDayOfWeek().equals(DayOfWeek.FRIDAY))){
            firstDayOfTheYear = firstDayOfTheYear.plusDays(1);
        }

        return firstDayOfTheYear.plusDays(7);
    }
    public static boolean isWorkingDay(LocalDate day){
        return !(FREE_DAYS.contains(day.getDayOfWeek()));
    }

    public static LocalDate findMonday(LocalDate day){
        if(day.getDayOfWeek() != DayOfWeek.FRIDAY){
            throw new IllegalArgumentException("Metoda findMonday musi byc wywolana dla piatku");
        }
        return  day.minusDays(4);
    }

    public static boolean isLastWorkingDayOfYear(LocalDate day){
        if(day.getMonth()== Month.DECEMBER){
            return isLastWorkingDayOfMonth(day);
        }else{
            return false;
        }
    }

    public static boolean isSecondFriday(LocalDate day,LocalDate secondFridayOfYear){
        int days = day.getDayOfYear() - secondFridayOfYear.getDayOfYear();

        return days % 14 == 0;
    }

    public static int countWorkingDays(LocalDate fromDate,LocalDate toDate){
        int workingDays = 0;
        do{
            if((isWorkingDay(fromDate))){
                workingDays++;
            }
            fromDate = fromDate.plusDays(1);
        }
        while (fromDate.isBefore(toDate)|| fromDate.isEqual(toDate));

        return workingDays;
    }

    public static boolean isLastWorkingDayOfMonth(LocalDate day){
        if(FREE_DAYS.contains(day.getDayOfWeek()) ){
            return false;

        }else if(day.getDayOfWeek() == DayOfWeek.FRIDAY){
            return day.getDayOfMonth() >= day.lengthOfMonth()-2;
        }
        else {
            return day.getDayOfMonth()== day.lengthOfMonth();
        }

    }
}
