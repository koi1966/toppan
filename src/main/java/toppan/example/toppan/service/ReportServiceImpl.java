package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.models.ReportData;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;


@Service
@Slf4j   // Логер
public class ReportServiceImpl implements ReportService {

    private final RubinWeekRepository rubinWeekRepository;
    private final CreateDoc createDoc;
    private final PidrozdilRepository pidrozdilRepository;
    private final NameFileDoc nameFileDoc;

    // Alt + Enter - вызывает конструктор
//    String separator = File.separator;
    public ReportServiceImpl(RubinWeekRepository rubinWeekRepository, CreateDoc createDoc, PidrozdilRepository pidrozdilRepository, NameFileDoc nameFileDoc) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.createDoc = createDoc;
        this.pidrozdilRepository = pidrozdilRepository;
        this.nameFileDoc = nameFileDoc;
    }

    @Override
    public void createMonthlyReport(LocalDate from, LocalDate to, String tsc) {
        LocalDate now = LocalDate.now();
        LocalDate startDateOld, endDateOld;
        startDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth());
        endDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth());
        ReportData reportData;
        if (tsc.equals("РСЦ 1840")) {
            log.info("Помісячний звіт РСЦ, date from: {}, to: {}, tsc: {}", from, to, tsc);
            reportData = new ReportData(rubinWeekRepository.setRubinDate(from, to, startDateOld, endDateOld));
        } else {
            log.info("Помісячний звіт ТСЦ, date from: {}, to: {}, tsc: {}", from, to, tsc);
            reportData = new ReportData(rubinWeekRepository.getRubinDateTSC(from, to, startDateOld, endDateOld, tsc));
        }

        String filename = nameFileDoc.NameMonthFileDoc(tsc);

        String path = null;
        try {
            path = createDoc.createDoc(reportData, tsc, to, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmailService.send(pidrozdilRepository.setEmailPidrozdil(tsc), path, tsc);
    }

    @Override
    public void createWeekReportTSC(LocalDate from, LocalDate to, String tsc) {
        LocalDate firstYear = LocalDate.now().with(firstDayOfYear());

        ReportData reportData;
        if (tsc.equals("РСЦ 1840")) {
            log.info("Тижневий ЗВІТ РСЦ, date from: {}, to: {}, tsc: {}", from, to, tsc);
            reportData = new ReportData(rubinWeekRepository.setWeekPrintRSC(from, to, firstYear));
        } else {
            log.info("Тижневий ЗВІТ ТСЦ, date from: {}, to: {}, tsc: {}", from, to, tsc);
            reportData = new ReportData(rubinWeekRepository.setWeekPrint2022(from, to, firstYear, tsc));
        }

        String fileNameDoc = nameFileDoc.NameWeekFileDoc(tsc);

        String path = null;
        try {
//            path =  createDoc.createDoc(reportData, minusYear, minusMonthUA, monthUA, yearTxt, tsc, filename);
            path = createDoc.createDoc(reportData, tsc, to, fileNameDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EmailService.send(pidrozdilRepository.setEmailPidrozdil(tsc), path, tsc);
    }

}
