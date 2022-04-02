package toppan.example.toppan.service;

import org.springframework.stereotype.Service;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.models.ReportData;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;
import toppan.example.toppan.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


@Service
//@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final RubinWeekRepository rubinWeekRepository;
    private final CreateDoc createDoc;
    private final PidrozdilRepository pidrozdilRepository;
    // Alt + Enter - вызывает конструктор
    String separator = File.separator;
    public ReportServiceImpl(RubinWeekRepository rubinWeekRepository, CreateDoc createDoc, PidrozdilRepository pidrozdilRepository) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.createDoc = createDoc;
        this.pidrozdilRepository = pidrozdilRepository;
    }

    @Override
    public void createMonthlyReport(LocalDate from, LocalDate to, String tsc) {
        LocalDate now = LocalDate.now();
        LocalDate startDateOld, endDateOld;
        startDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth());
        endDateOld = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth());

        ReportData reportData = new ReportData(rubinWeekRepository.getRubinDateTSC(from, to, startDateOld, endDateOld, tsc));

        int year = now.getYear();
        String yearTxt = Integer.toString(year);
        String monthUA = DateUtils.MonthNamUA.getNameMonth(now.getMonthValue());

//        EmailSender.send("o.klymchuk@zhi.hsc.gov.ua");
        String filename = "c:"+ separator +"rsc1840"+separator+"Temp_rubin_Mounth.docx";
        String path = "";
        try {
            path =  createDoc.createDoc(reportData, monthUA, yearTxt, tsc, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        filename ="c:" + separator + "rsc1840" + separator + "Rubin" +tsc+".docx";
//         "c:" + separator + "rsc1840" + separator + "Rubin" +tsc+".docx";
        EmailService.send(pidrozdilRepository.setEmailPidrozdil(tsc), path);
//        EmailFilename.send("o.klymchuk@zhi.hsc.gov.ua",filename);
    }
}
