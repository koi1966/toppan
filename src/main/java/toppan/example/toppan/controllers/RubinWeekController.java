package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.models.Pidrozdil;
import toppan.example.toppan.models.Rubin_week;
import toppan.example.toppan.models.repo.*;
import toppan.example.toppan.service.ReportService;
import toppan.example.toppan.utils.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.previous;

@Slf4j   // Логер
@Controller
class RubinWeekController {
    private final RubinWeekRepository rubinWeekRepository;
    private final PidrozdilRepository pidrozdilRepository;
    private final ReportService reportService;

    public RubinWeekController(RubinWeekRepository rubinWeekRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel, RubinYearRepository rubinYearRepository, RubinRepository rubinRepository, MonthRepository monthRepository, CreateDoc createDoc, ReportService reportService) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.reportService = reportService;
    }

    @GetMapping("/rubin/week/rubin-week-view")
    public String rubinWeekViewAll(HttpServletRequest request, Model model) throws ParseException {
        log.info("GetMapping(.rubin.week.rubin-week-view)");

        final LocalDate today = LocalDate.now();  // берем сегодняшнюю дату
        final LocalDate thisPastSunday = today.with(previous(SUNDAY));  // берем прошедшее воскресенье

        String ip_user = request.getRemoteAddr(); //  вытягивает IP копма с которого вносят информацию
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
        String ip = ip_user.substring(0, end); // узнаеп подсеть  172.0.0
        Pidrozdil tsc = pidrozdilRepository.findByIp(ip);//  по подсети узнаем из какого ТСЦ зашли работать

        List<Rubin_week> rubinList = rubinWeekRepository.getAllByDataBetweenAndPidrozdilPidrozdil(thisPastSunday, today, tsc.getPidrozdil(), Sort.by("pidrozdil").and(Sort.by("data")));

        model.addAttribute("rubinList", rubinList);

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findByOrderByPidrozdilAsc();
        model.addAttribute("week_appeal", "0");
        model.addAttribute("week_issued", "0");
        model.addAttribute("pidrozdilList", pidrozdilList);
        model.addAttribute("dat", thisPastSunday);
        model.addAttribute("dat_last", today);
        return "rubin/week/rubin-week-view";
    }

    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=search")
    public String rubinSearchTSC(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                 @RequestParam("data_vpo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                 @RequestParam("p_tsc") String tsc,
                                 Model model) {

        List<Rubin_week> rubinList;

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findByOrderByPidrozdilAsc();
        model.addAttribute("pidrozdilList", pidrozdilList);
        String rubinSUM;
        if (tsc.equals("РСЦ 1840")) {
            rubinList = rubinWeekRepository.setWeekRSC(from, to);
            rubinSUM = rubinWeekRepository.setWeekRSCSum(from, to);

        } else {
            rubinList = rubinWeekRepository.setListDateRubinWeek(from, to, tsc);
            rubinSUM = rubinWeekRepository.setWeekAllTSCSum(from, to, tsc);
        }

        String[] strSum = rubinSUM.split(",");
        model.addAttribute("rubinList", rubinList);
        model.addAttribute("week_appeal", strSum[0]);
        model.addAttribute("week_issued", strSum[1]);
        model.addAttribute("dat", from);
        model.addAttribute("dat_last", to);
        model.addAttribute("p_tsc", tsc);
        return "rubin/week/rubin-week-view";
    }

    /**
     * Тижневий ЗВІТ    ТСЦ
     */
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print")
    public String rubinPrintWeekTSC(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam("data_vpo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                    @RequestParam("p_tsc") String tsc,
                                    @RequestParam("etsc") String etsc) {
        String em = etsc;
        log.info("Тижневий ЗВІТ ТСЦ (action=print), from: {}, to: {}, tsc: {}", from, to, tsc);
        reportService.createWeekReportTSC(from, to, tsc);
//        String rubinWekStr = rubinWeekRepository.setWeekPrint2022(from, to, firstYear, tsc);
//
//        boolean isEmpty = rubinWekStr == null || rubinWekStr.trim().length() == 0;
//        if (isEmpty) {
//            rubinWekStr = "0,0,0,0";
//        }

        return "redirect:/rubin/week/rubin-week-view";
    }

    /**
     * Помісячний звіь ТСЦ
     */
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print_month")
    public String rubinPrintMonth(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                  @RequestParam("data_vpo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                  @RequestParam("p_tsc") String tsc) {
        log.info("Generate monthly report (action=print_month), date from: {}, to: {}, tsc: {}", from, to, tsc);
        reportService.createMonthlyReport(from, to, tsc);
        return "redirect:/";
    }

    /**
     * Недільний звіт ТЦС
     */
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print_week_rsc")
    public String rubunPrintWeekRSC(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam("data_vpo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                    @RequestParam("p_tsc") String tsc) {
        log.info("Generate monthly report (action=print_week_rsc), date from: {}, to: {}, tsc: {}", from, to, tsc);

        reportService.createMonthlyReport(from, to, tsc);

        return "redirect:/rubin/week/rubin-week-view";
    }

    /**
     * Помісячний звіт РСЦ
     *
     * @param data_v
     * @param data_last
     * @param tsc_front
     * @return
     */
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print_month_rsc")
    public String rubinPrintMonthRSC(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v,
                                     @RequestParam("data_vpo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_last,
                                     @RequestParam("p_tsc") String tsc_front) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now = LocalDate.now();
        String startPreviousYear = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.firstDayOfMonth()).format(format);
        String endPreviousYear = now.minusMonths(1).minusYears(1).with(TemporalAdjusters.lastDayOfMonth()).format(format);

        Date startDateOld = null;
        try {
            startDateOld = new SimpleDateFormat("dd.MM.yyyy").parse(startPreviousYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endDateOld = null;
        try {
            endDateOld = new SimpleDateFormat("dd.MM.yyyy").parse(endPreviousYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        String rubinStr = rubinWeekRepository.setRubinDate(data_v, data_last, startDateOld, endDateOld);

        LocalDate localDate = data_last.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

//        rubinStr = rubinStr + ',' + tsc_front + ',' + String.valueOf(month) + "." + String.valueOf(year) + ',' + pidrozdilRepository.setEmailPidrozdil(tsc_front);
//        String[] str = rubinStr.split(",");

//        EmailSender.send("o.klymchuk@zhi.hsc.gov.ua");
        String filename = "c:/RSC1840/Temp_rubin_Mounth_1840.docx";

//        EmailFilename.send(str[6], filename);
        filename = "c:/RSC1840/Rubin.docx";
//        EmailFilename.send("it@zhi.hsc.gov.ua", filename);
//        EmailService.send(str[6], filename, tsc_front);

        return "redirect:/rubin/week/rubin-week-view";
    }

    /**
     * Внесення щоденної інформійії
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/rubin/week/rubin-add-week")
    public String rubinAddWeek(HttpServletRequest request, Model model) {

        String ip_user = request.getRemoteAddr();//        вытягивает IP копма с которого вносят информацию
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);

        String ip = ip_user.substring(0, end);//        узнаел подсеть

        Pidrozdil pidrozdil = pidrozdilRepository.findByIp(ip);
        if (pidrozdil == null || pidrozdil.getPidrozdil().isEmpty()) {
            return "redirect:/";
        }

        Date date = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();//        //Getting the default zone id
        Instant instant = date.toInstant();//Converting the date to Instant

        Rubin_week rubin_week = new Rubin_week();
        rubin_week.setPidrozdil(pidrozdil);
        rubin_week.setData(instant.atZone(defaultZoneId).toLocalDate());
        model.addAttribute("rubin_week", rubin_week);
        return "rubin/week/rubin-add-week";
    }

    @PostMapping("/rubin/week/rubin-add-week")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String rubinAddWeek(@RequestParam String pidrozdilName,
                               @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data_v,
                               @RequestParam int week_issued,
                               @RequestParam int week_appeal) {
        Pidrozdil pidrozdil = pidrozdilRepository.findById(pidrozdilName).get();
        Rubin_week rubin_week = new Rubin_week();
        rubin_week.setPidrozdil(pidrozdil);
        rubin_week.setData(data_v);
        rubin_week.setWeek_appeal(week_appeal);
        rubin_week.setWeek_issued(week_issued);
        rubinWeekRepository.save(rubin_week);
        return "redirect:/";
    }

    @GetMapping("/rubin/week/{id}") //  открыть для поштуного просмотра
    public String rubinWeekDetails(Model model,
                                   @PathVariable("id") long id) {

        Optional<Rubin_week> rubin = rubinWeekRepository.findById(id);//      находим и передаем єту одну запись на вюшку

        ArrayList<Rubin_week> res = new ArrayList<>();
        rubin.ifPresent(res::add);
        model.addAttribute("rubin_week_det", res);
        return "rubin/week/rubin-week-deteils";//       отображаем найденное на вюшке
    }

    @PatchMapping("/rubin/week/{id}/edit")
    public String update(@ModelAttribute("rubin") Rubin_week rubin_week) {
        rubinWeekRepository.save(rubin_week);
        return "redirect:rubin/week/rubin-week-view";
    }

    @GetMapping("/rubin/week/{id}/update") // после НАЖАТИЯ НА кнопкУ редактирование даннЫх
    public String rubinEdit(Model model, @PathVariable(value = "id") long id) {
        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin_week> rubin_week = rubinWeekRepository.findById(id);
        ArrayList<Rubin_week> res = new ArrayList<>();

        rubin_week.ifPresent(res::add);
        model.addAttribute("rubin_week_edit", res);
        return "rubin/week/rubin-week-edit";//       редактируем найденное на вюшке
    }

    @PostMapping("/rubin/week/{id}/update")  // нажата кнопка - Обновить
    public String rubinUpdate(Model model,
                              @PathVariable(value = "id") long id,
                              @RequestParam int week_appeal,
                              @RequestParam int week_issued,
                              @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v
    ) {
        Rubin_week rubin_week_up = rubinWeekRepository.findById(id).orElseThrow();
        rubin_week_up.setWeek_appeal(week_appeal);
        rubin_week_up.setWeek_issued(week_issued);
        rubinWeekRepository.save(rubin_week_up);
        return "redirect:/rubin/week/rubin-week-view";
    }

    @PatchMapping("/rubin/week/{id}/remove")
    public String remove(@ModelAttribute("rubin") Rubin_week rubin_week) {
        rubinWeekRepository.save(rubin_week);
        return "redirect:rubin/week/rubin-week-view";
    }

}
