package toppan.example.toppan.models;

import lombok.Data;
import toppan.example.toppan.utils.DateUtils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Data
public class ReportData {
    /**
     * Обращений за прошлый период
     */
    private String requestOld;
    /**
     * Выдано за прошлый период
     */
    private String issuedOld;
    /**
     * Обращений
     */
    private String request;
    /**
     * Выдано
     */
    private String issued;
    private String pidrozdilNumber;
    /**
    * Прошлий рік
     */
    private String minusYear;
    /**
     * Попередній місяць
     */
    private String minusMonthUA;
    /**
     * Місяць за номером
     */
    private String monthUA;
    /**
     * Рік
     */
    private String yearTxt;

    public ReportData(String reportData) {
        String[] strData = reportData.split(",");
        LocalDate now = LocalDate.now();
        LocalDate startDateOld, endDateOld, minusMount;

        requestOld = strData[0];
        issuedOld = strData[1];
        request = strData[2];
        issued = strData[3];
        pidrozdilNumber = strData[4];

        startDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth());
//        endDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth());
        minusMount = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        int year = now.getYear();
        int oldYear = startDateOld.getYear();
        yearTxt = Integer.toString(year);

        monthUA = DateUtils.MonthNamUA.getNameMonth(now.getMonthValue());
        minusMonthUA = DateUtils.MonthNamUA.getNameMonth(minusMount.getMonthValue());
        minusYear = DateUtils.MonthNamUA.getNameMonth(startDateOld.getMonthValue());
        minusYear = minusYear.concat(" ")+ oldYear;
    }

}
