package toppan.example.toppan.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public enum MonthNamUA {
        Січнь(1), Лютий(2), Березень(3), Квітень(4), Травень(5),
        Липень(6), Червень(7), Серпень(8), Вересень(9), Жовтень(10),
        Листопад(11), Грудень(12);

        private final int number;

        MonthNamUA(int number) {
            this.number = number;
        }

        public static String getNameMonth(int number) {
            MonthNamUA[] values = MonthNamUA.values();
            for (MonthNamUA month : values) {
                if (month.number == number) {
                    return month.name();
                }
            }
            return null;
        }

    }

    ;

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String monthNamesUA(LocalDate now) {

        String[] monthNamesUA = {"Січнь", "Лютий", "Березень", "Квітень", "Травень", "Липень", "Червень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"};
//        String monthUA = monthNamesUA[now.getMonthValue() - 1];
        return monthNamesUA[now.getMonthValue() - 1];
    }


}

//        LocalDateTime ldt1 = LocalDateTime.now();
//        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//// Форматирование ldt1 в строку и вывод: 2019-12-05 20:17:40
//        String dateStr1 = dtf1.format(ldt1);
//        System.out.println(dateStr1);
//
//        String dateStr2 = "2019-12-05 20:16:42";
//        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("гггг-мм-дд чч час мм мин сс секунда");
//// Разобрать строку даты в дату и вывести: 2019-12-05T20: 16: 42
//        LocalDateTime ldt2 = LocalDateTime.parse(dateStr2,dtf2);
//        System.out.println(ldt2);
//        String str = "2019-12-05";