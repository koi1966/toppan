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
import toppan.example.toppan.models.Rubin_month;
import toppan.example.toppan.models.Rubin_week;
import toppan.example.toppan.models.repo.*;
import toppan.example.toppan.service.EmailService;
import toppan.example.toppan.service.ReportService;
import toppan.example.toppan.utils.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
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
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.previous;

@Slf4j   // Логер
@Controller
class RubinWeekController {

    private final RubinWeekRepository rubinWeekRepository;
    private final PidrozdilRepository pidrozdilRepository;
    private final CreateExcel createExcel;
    private final RubinYearRepository rubinYearRepository;
    private final RubinRepository rubinRepository;
    private final MonthRepository monthRepository;
    private final CreateDoc createDoc;
    private final ReportService reportService;

    public RubinWeekController(RubinWeekRepository rubinWeekRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel, RubinYearRepository rubinYearRepository, RubinRepository rubinRepository, MonthRepository monthRepository, CreateDoc createDoc, ReportService reportService) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.createExcel = createExcel;
        this.rubinYearRepository = rubinYearRepository;
        this.rubinRepository = rubinRepository;
        this.monthRepository = monthRepository;
        this.createDoc = createDoc;
        this.reportService = reportService;
    }

    @GetMapping("/rubin/week/rubin-week-view1") // тестовое окно
    public String rubinWeekViewAll1() {

        return "rubin/week/rubin-week-view1";
    }

    @GetMapping("/rubin/week/rubin-week-view")
    public String rubinWeekViewAll(HttpServletRequest request, Model model) throws ParseException {
        log.info("GetMapping(.rubin.week.rubin-week-view)");

        final LocalDate today = LocalDate.now();  // берем сегодняшнюю дату
//        final LocalDate nextSunday = today.with(next(SUNDAY)); // берем будущее воскресенье
        final LocalDate thisPastSunday = today.with(previous(SUNDAY));  // берем прошедшее воскресенье

        String ip_user = request.getRemoteAddr(); //  вытягивает IP копма с которого вносят информацию
//        ip_user="172.0.0.0";
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=search")
    public String rubinSearchTSC(@RequestParam("data_v") String data_s,
                                 @RequestParam("data_vpo") String data_last_str,
                                 @RequestParam("p_tsc") String tsc,
//                              @RequestParam(value="action", required=true) String action,
                                 Model model) {

        List<Rubin_week> rubinList;

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date data_v = null;
        try {
            data_v = sdf2.parse(data_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date data_last = null;
        try {
            data_last = sdf2.parse(data_last_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findByOrderByPidrozdilAsc();
        model.addAttribute("pidrozdilList", pidrozdilList);
        String rubinSUM;
        if (tsc.equals("РСЦ 1840")) {
            rubinList = rubinWeekRepository.setWeekRSC(data_v, data_last);
            rubinSUM = rubinWeekRepository.setWeekRSCSum(data_v, data_last);

        } else {
            rubinList = rubinWeekRepository.setListDateRubinWeek(data_v, data_last, tsc);
            rubinSUM = rubinWeekRepository.setWeekAllTSCSum(data_v, data_last, tsc);
        }

        String[] strSum = rubinSUM.split(",");
        model.addAttribute("rubinList", rubinList);
        model.addAttribute("week_appeal", strSum[0]);
        model.addAttribute("week_issued", strSum[1]);
        model.addAttribute("dat", data_s);
        model.addAttribute("dat_last", data_last_str);
        model.addAttribute("p_tsc", tsc);
        return "rubin/week/rubin-week-view";
    }

    /**
     * Тижневий ЗВІТ    ТСЦ
     *
     * @param data_sart_str
     * @param data_end_str
     * @param tsc_front
     * @param model
     * @param request
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Тижневий ЗВІТ    ТСЦ
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print")
    public String rubinPrintWeekTSC(@RequestParam("data_v") String data_sart_str,
                                    @RequestParam("data_vpo") String data_end_str,
                                    @RequestParam("p_tsc") String tsc_front,
                                    Model model, HttpServletRequest request) {

        //  сверяем ТСЦ которое создает отчет и сравниваем IP копма с которого вносят информацию
//        String ip_user = request.getRemoteAddr();
//        int ip_tsc = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
//
//        String ip = ip_user.substring(0, ip_tsc);//        узнаеп подсеть
//        String tsc = pidrozdilRepository.setNamePidrozdil(ip);//        по подсети узнаем из какого ТСЦ зашли работать
//
//        if (tsc != "РСЦ 1840") {
//            if (tsc_front != tsc) {
//                return "redirect:/Error";//  если разные ТСЦ то на сторінку з поилкою
//            }
//        }
//     сверяем ТСЦ которое создает отчет и сравниваем IP копма с которого вносят информацию

        //  отчет по датам из фронта
// ************************************************************************************************
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date data_sart = null;
        try {
            data_sart = sdf2.parse(data_sart_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date data_end = null;
        try {
            data_end = sdf2.parse(data_end_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String firstYearStr = LocalDate.now().with(firstDayOfYear()).toString();
        Date firstYear = null;
        try {
            firstYear = sdf2.parse(firstYearStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//  получение начало текущего года
//        Date date = Date.from(LocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        java.util.Date firstYear = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(LocalDate.now().with(firstDayOfYear())));

//        "SELECT sum(week_appeal) as week_appeal, " +
//                "sum(week_issued) as week_issued, " +
//                "(SELECT sum(ry.week_appeal) from rubin_week ry where ry.pidrozdil = :tsc and ry.data_v >= :firstYear and ry.data_v <= :endDate) as year_appeal, " +
//                "(SELECT sum(ri.week_issued) from rubin_week ri where ri.pidrozdil = :tsc and ri.data_v >= :firstYear and ri.data_v <= :endDate) as year_issued, " +
//                "RIGHT(pidrozdil, 4) as pidrozdil " +

        String rubinWekStr = rubinWeekRepository.setWeekPrint2022(data_sart, data_end, firstYear, tsc_front);

        boolean isEmpty = rubinWekStr == null || rubinWekStr.trim().length() == 0;
        if (isEmpty) {
            rubinWekStr = "0,0,0,0";
        }

        rubinWekStr = rubinWekStr + ',' + data_end_str + "," + tsc_front + ',' + pidrozdilRepository.setEmailPidrozdil(tsc_front);
        String filename;
        switch (tsc_front) {
            case "РСЦ 1840":
                System.out.println("РСЦ 1840");
                filename = "c:/RSC1840/Temp_rubin_1840.docx";
                break;
            case "ТСЦ 1841":
                System.out.println("Тижневий ТСЦ 1841");
                filename = "c:/RSC1840/Temp_rubin_1841.docx";
                break;
            case "ТСЦ 1842":
                System.out.println("Тижневий ТСЦ 1842");
                filename = "c:/RSC1840/Temp_rubin_1842.docx";
                break;
            case "ТСЦ 1843":
                System.out.println("Тижневий ТСЦ 1843");
                filename = "c:/RSC1840/Temp_rubin_1843.docx";
                break;
            case "ТСЦ 1844":
                System.out.println("Тижневий ТСЦ 1844");
                filename = "c:/RSC1840/Temp_rubin_1844.docx";
                break;
            case "ТСЦ 1845":
                System.out.println("Тижневий ТСЦ 1845");
                filename = "c:/RSC1840/Temp_rubin_1845.docx";
                break;
            default:
                System.out.println("Oooops, Тижневий  something wrong !");
                String setarator = File.separator;
                filename = "C:" + setarator + "RSC1840" + setarator + "Temp_rubin.docx";
        }
        // *********************************************
        //  Doc
//        String[] str = rubinWekStr.split(",");
//        try {
//            createDoc.createDoc(rubinWekStr, filename);//  Внесение полученной информации в Doc и отправка его на почту
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
// *********************************************
//        EmailService.send(str[7], "c:/RSC1840/rubin.docx");
        return "redirect:/rubin/week/rubin-week-view";
    }

    /**
     * Помісячний звіь ТСЦ
     *
     * @param from
     * @param to
     * @param tsc
     * @return
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
     *
     * @param data_sart_str
     * @param data_end_str
     * @param tsc_front
     * @return
     */
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print_week_rsc")
    public String rubunPrintWeekRSC(@RequestParam("data_v") String data_sart_str,
                                    @RequestParam("data_vpo") String data_end_str,
                                    @RequestParam("p_tsc") String tsc_front) {

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date data_sart = null;
        try {
            data_sart = sdf2.parse(data_sart_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date data_end = null;
        try {
            data_end = sdf2.parse(data_end_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  получение начало текущего года
        String firstYearStr = LocalDate.now().with(firstDayOfYear()).toString();
        Date firstYear = null;
        try {
            firstYear = sdf2.parse(firstYearStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String data_end_str2 = formatter.format(data_end);

        String rubinWekStr = rubinWeekRepository.setWeekPrintRSC(data_sart, data_end, firstYear);
        rubinWekStr = rubinWekStr + ',' + tsc_front + ',' + data_end_str2 + "," + tsc_front + ',' + pidrozdilRepository.setEmailPidrozdil(tsc_front);
        String filename = "c:/RSC1840/Temp_rubin_1840.docx";
// *********************************************
//        //  Doc
//        try {
//            createDoc.createDoc(rubinWekStr, filename);//  Внесение полученной информации в Doc и отправка его на почту
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // *********************************************
//        filename = "c:/RSC1840/rubin.docx";

        String[] str = rubinWekStr.split(",");
        EmailService.send(str[7], "c:/RSC1840/rubin.docx", tsc_front);

//        *****************************************************************************************************
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
// ***********************************************************************************************************************
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
        String rubinStr = rubinWeekRepository.setRubinDate(data_v, data_last, startDateOld, endDateOld);

        LocalDate localDate = data_last.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
//        String mount2 = localDate.getMonth();
//        Month jan = Month.of(1);
//        Locale loc = Locale.forLanguageTag("ru");
//        System.out.println(jan.getDisplayName(TextStyle.FULL_STANDALONE, loc)); // Вернёт Январь/січень

        rubinStr = rubinStr + ',' + tsc_front + ',' + String.valueOf(month) + "." + String.valueOf(year) + ',' + pidrozdilRepository.setEmailPidrozdil(tsc_front);
        String[] str = rubinStr.split(",");

//        EmailSender.send("o.klymchuk@zhi.hsc.gov.ua");
        String filename = "c:/RSC1840/Temp_rubin_Mounth_1840.docx";
        // *********************************************
//        try {
//            createDoc.createDoc(rubinStr, filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // *********************************************
//        EmailFilename.send(str[6], filename);
        filename = "c:/RSC1840/Rubin.docx";
//        EmailFilename.send("it@zhi.hsc.gov.ua", filename);
        EmailService.send(str[6], filename, tsc_front);
//        **************************************************************************************************************
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
//        ip_user="172.0.0.0";
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
//        Rubin_week rubin_week = new Rubin_week(pidrozdil, data_v,week_appeal, week_issued );
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
//                              @RequestParam String pidrozdil,
                              @RequestParam int week_appeal,
                              @RequestParam int week_issued,
                              @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v
    ) {
        Rubin_week rubin_week_up = rubinWeekRepository.findById(id).orElseThrow();
//        rubin_week_up.setPidrozdil(pidrozdil);
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

    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=month_stop")
    public String rubinAddMounh() {

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date data_v = null;
        try {
            data_v = sdf2.parse("2021-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Rubin_month rubinMonth = new Rubin_month();

        rubinMonth.setData_v(data_v);
        rubinMonth.setMonth_appeal(111);
        rubinMonth.setMonth_issued(111);
        rubinMonth.setMonth_year("12.2021");
        rubinMonth.setPidrozdil("ТСЦ 1843");
        monthRepository.save(rubinMonth);
        return "redirect:/";
    }

//    print_month_rsc

//    String[] strings = {"foo","bar"};
//    List<String> l = Arrays.asList(strings);
//    Ctrl+Shift+Space
}
