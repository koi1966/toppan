package toppan.example.toppan.service;

import java.time.LocalDate;

public interface ReportService {
    void createMonthlyReport(LocalDate from, LocalDate to, String tsc);
    void createWeekReportTSCImp(LocalDate from, LocalDate to, String tsc);
}
