package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.models.ReportData;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;
import toppan.example.toppan.utils.DateUtils;

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
        LocalDate startDateOld, endDateOld, minusMount;
        startDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth());
        endDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth());
//        minusMount = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());


        ReportData reportData = new ReportData(rubinWeekRepository.getRubinDateTSC(from, to, startDateOld, endDateOld, tsc));

//        int year = now.getYear();
//        int oldYear = startDateOld.getYear();
//
//        String yearTxt = Integer.toString(year);
//
//        String monthUA = DateUtils.MonthNamUA.getNameMonth(now.getMonthValue());
//        String minusMonthUA = DateUtils.MonthNamUA.getNameMonth(minusMount.getMonthValue());
//        String minusYear = DateUtils.MonthNamUA.getNameMonth(startDateOld.getMonthValue());
//        minusYear = minusYear +" "+ oldYear;
//        EmailSender.send("o.klymchuk@zhi.hsc.gov.ua");

        String filename = nameFileDoc.NameWeekFileDoc(tsc);
//        String filename = "c:\\rsc1840\\Temp_rubin_Mounth.docx";
        String path = null;
        try {
//            path =  createDoc.createDoc(reportData, minusYear, minusMonthUA, monthUA, yearTxt, tsc, filename);
            path =  createDoc.createDoc(reportData, tsc, to, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EmailService.send(pidrozdilRepository.setEmailPidrozdil(tsc), path, tsc);
//        EmailFilename.send("o.klymchuk@zhi.hsc.gov.ua",filename);
    }

    @Override
    public void createWeekReportTSCImp(LocalDate from, LocalDate to, String tsc) {
        LocalDate firstYear = LocalDate.now().with(firstDayOfYear());
        log.info("Тижневий ЗВІТ ТСЦ (action=print), date from: {}, to: {}, tsc: {}", from, to, tsc);
        ReportData reportData = new ReportData(rubinWeekRepository.setWeekPrint2022(from, to, firstYear, tsc));
        String fileNameDoc = nameFileDoc.NameWeekFileDoc(tsc);

//        String fileNameDoc = "c:\\rsc1840\\Temp_rubin_Mounth.docx";
        String path = null;
        try {
//            path =  createDoc.createDoc(reportData, minusYear, minusMonthUA, monthUA, yearTxt, tsc, filename);
            path =  createDoc.createDoc(reportData, tsc, to, fileNameDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EmailService.send(pidrozdilRepository.setEmailPidrozdil(tsc), path, tsc);
    }

}
