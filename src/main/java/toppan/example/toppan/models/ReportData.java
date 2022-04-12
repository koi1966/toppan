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

    public ReportData(String reportData) {
        String[] strData = reportData.split(",");

        requestOld = strData[0];
        issuedOld = strData[1];
        request = strData[2];
        issued = strData[3];
        pidrozdilNumber = strData[4];

        LocalDate now = LocalDate.now();
        LocalDate startDateOld, endDateOld, minusMount;
        startDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth());
        endDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth());
        minusMount = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        int year = now.getYear();
        int oldyear = startDateOld.getYear();
        String yearTxt = Integer.toString(year);

        String monthUA = DateUtils.MonthNamUA.getNameMonth(now.getMonthValue());
        String minusUA = DateUtils.MonthNamUA.getNameMonth(minusMount.getMonthValue());
        String minusYears = DateUtils.MonthNamUA.getNameMonth(startDateOld.getMonthValue());
        minusYears = minusYears +" "+ oldyear;


    }

}
