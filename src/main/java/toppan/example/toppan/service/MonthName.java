package toppan.example.toppan.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class MonthName {
    public static String MonthNameRodo() {
        Calendar calendar = Calendar.getInstance();
        String[] monthNames = {"Січнь", "Лютий", "Березень", "Квітень", "Травень", "Липень", "Червень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"};
        String month2 = monthNames[calendar.get(Calendar.MONTH)];
        return null;
    }
}
