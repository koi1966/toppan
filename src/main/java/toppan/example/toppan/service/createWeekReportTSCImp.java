package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.models.ReportData;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
@Slf4j   // Логер
@Service
public class createWeekReportTSCImp implements ReportService {
    private final RubinWeekRepository rubinWeekRepository;
    private final CreateDoc createDoc;
    private final PidrozdilRepository pidrozdilRepository;
    private final NameFileDoc nameFileDoc;

    public createWeekReportTSCImp(RubinWeekRepository rubinWeekRepository, CreateDoc createDoc, PidrozdilRepository pidrozdilRepository, NameFileDoc nameFileDoc) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.createDoc = createDoc;
        this.pidrozdilRepository = pidrozdilRepository;
        this.nameFileDoc = nameFileDoc;
    }

    @Override
    public void createMonthlyReport(LocalDate from, LocalDate to, String tsc) {

        LocalDate firstYear = LocalDate.now().with(firstDayOfYear());
        log.info("Тижневий ЗВІТ ТСЦ (action=print), date from: {}, to: {}, tsc: {}", from, to, tsc);
        ReportData reportData = new ReportData(rubinWeekRepository.setWeekPrint2022(from, to, firstYear, tsc));
        String fileNameDoc = nameFileDoc.NameWeekFileDoc(tsc);


    }


}
