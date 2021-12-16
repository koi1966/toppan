package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.createDoc.CreateExilMonth;
import toppan.example.toppan.models.*;
import toppan.example.toppan.models.repo.*;
import toppan.example.toppan.utilities.EmailSender;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

@Controller
class RubinWeekController {

    private final RubinWeekRepository rubinWeekRepository;
    private final PidrozdilRepository pidrozdilRepository;
    private final CreateExcel createExcel;
    private final RubinYearRepository rubinYearRepository;
    private final RubinRepository rubinRepository;
    private final MonthRepository monthRepository;

    public RubinWeekController(RubinWeekRepository rubinWeekRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel, RubinYearRepository rubinYearRepository, RubinRepository rubinRepository, MonthRepository monthRepository) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.createExcel = createExcel;
        this.rubinYearRepository = rubinYearRepository;
        this.rubinRepository = rubinRepository;
        this.monthRepository = monthRepository;
    }

    @GetMapping("/rubin/week/rubin-week-view")
    public String rubinWeekViewAll(HttpServletRequest request, Model model) throws ParseException {

        final LocalDate today = LocalDate.now();  // берем сегдняшнюю дату
//        final LocalDate nextSunday = today.with(next(SUNDAY)); // берем будущее воскресенье
        final LocalDate thisPastSunday = today.with(previous(SUNDAY));  // берем прошедшее воскресенье
//        LocalDate dat_firs_t = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());  //        Берем первое число текущего месяца
        String dat_first = thisPastSunday.toString();

        Date dat_f = new SimpleDateFormat("yyyy-MM-dd").parse(dat_first);//  dat_first - переводим в тип Date  первое число месяца
        String date_s = LocalDate.now().toString(); // берем локальную дату переводим в String

        Date data_v = null;
        try {
            data_v = new SimpleDateFormat("yyyy-MM-dd").parse(date_s);  //  Локальная дата без времени
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ip_user = request.getRemoteAddr(); //        вытягивает IP копма с которого вносят информацию
//        ip_user="172.0.0.0";
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
        String ip = ip_user.substring(0, end); //        узнаеп подсеть  172.0.0
        String tsc = pidrozdilRepository.setNamePidrozdil(ip);//        по подсети узнаем из какого ТСЦ зашли работать

        List<Rubin_week> rubinList = rubinWeekRepository.setListDateRubinWeek(dat_f, data_v, tsc);
        model.addAttribute("rubinList", rubinList);

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findAll();
        model.addAttribute("pidrozdilList", pidrozdilList);

        model.addAttribute("dat", dat_first);
        model.addAttribute("dat_last", date_s);
        return "rubin/week/rubin-week-view";
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=search")
    public String rubinViewData(@RequestParam("data_v") String data_s,
                                @RequestParam("data_vpo") String data_last_str,
                                @RequestParam("p_tsc") String tsc,
//                              @RequestParam(value="action", required=true) String action,
                                Model model) {

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

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findAll();
        model.addAttribute("pidrozdilList", pidrozdilList);

        List<Rubin_week> rubinList = rubinWeekRepository.setListDateRubinWeek(data_v, data_last, tsc);
        model.addAttribute("rubinList", rubinList);

        model.addAttribute("dat", data_s);
        model.addAttribute("dat_last", data_last_str);
        model.addAttribute("p_tsc", tsc);
        return "rubin/week/rubin-week-view";
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print")
    public String rubinViewDataPrint(@RequestParam("data_v") String data_sart_str,
                                     @RequestParam("data_vpo") String data_end_str,
                                     @RequestParam("p_tsc") String tsc_front,
                                     Model model, HttpServletRequest request) {

        //  сверяем ТСЦ которое создает отчет и сравниваем IP копма с которого вносят информацию
        String ip_user = request.getRemoteAddr();
        int ip_tsc = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);

        String ip = ip_user.substring(0, ip_tsc);//        узнаеп подсеть
        String tsc = pidrozdilRepository.setNamePidrozdil(ip);//        по подсети узнаем из какого ТСЦ зашли работать

        if (tsc != "РСЦ 1840") {
            if (tsc_front != tsc) {
                return "redirect:/";//  если разные ТСЦ то на головну сторынку
            }
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date data_v = null;
        try {
            data_v = sdf2.parse(data_sart_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date data_last = null;
        try {
            data_last = sdf2.parse(data_end_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String rubinWekStr = rubinWeekRepository.setWeekPrint(data_v, data_last, tsc);
        rubinWekStr = rubinWekStr + ',' + "(станом на " + data_end_str + ")," + tsc + ',' + pidrozdilRepository.setEmailPidrozdil(tsc);

        try {
            createExcel.CreateF(rubinWekStr);//  Внесение полученной информации в ексл и отправка его на почту
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tsc_data = rubinRepository.setDatePidrozdil(data_last, tsc);//  Проверяем на дубликат отчета
        boolean isEmpty = tsc_data == null || tsc_data.trim().length() == 0;
        if (!isEmpty) {

            return "redirect:/";
        }

        String[] str = rubinWekStr.split(",");
        EmailSender.send(str[6]);
        //  Запись сформированного отчета по таблицам
        Rubin rubin = new Rubin();
        rubin.setPidrozdil(tsc);

        LocalDate dat_f = LocalDate.parse(data_end_str);
//        Date dat_f =new SimpleDateFormat("dd/MM/yyyy").parse(data_last_str);
        rubin.setData_v(dat_f);

        rubin.setWeek(Integer.parseInt(str[0]));
        rubin.setWeek_1(Integer.parseInt(str[1]));

        rubin.setYear_0(Integer.parseInt(str[2]));
        rubin.setYear_1(Integer.parseInt(str[3]));
        rubinRepository.save(rubin);

        Rubin_year rubin_year = new Rubin_year();//  Записать недельные данные в таблицу rubin_year
        rubin_year.setPidrozdil(tsc);
        rubin_year.setData_v(data_last);
        rubin_year.setYear_appeal(Integer.parseInt(str[2]));
        rubin_year.setYear_issued(Integer.parseInt(str[3]));
        rubinYearRepository.save(rubin_year);  //  Запись в годовую таблицю

        return "redirect:/rubin/week/rubin-week-view";
    }

    @PostMapping(value = "/rubin/week/rubin-week-view", params = "action=print_month")
    public String rubinMouth(@RequestParam("data_v") String data_s,
                             @RequestParam("data_vpo") String data_last_str,
                             @RequestParam("p_tsc") String tsc_front,
                             Model model,HttpServletRequest request){
       //  Первая и последяя дата предідущего месяца  = Почитать =-> С помощью Java.Time очень легко писать код.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String startDate = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(format);
        String endDate = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(format);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start_Date = null;
        try {
            start_Date = sdf2.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date end_Date = null;
        try {
            end_Date = sdf2.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // проверить на дубликат,
        // ***********************************************
        DateTimeFormatter format_2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String newString = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(format_2);
        String month_year = newString.substring(3);
        String tsc_data = monthRepository.setMonthPidrozdil(month_year, tsc_front);//  Проверяем на дубликат отчета
        boolean isEmpty = tsc_data == null || tsc_data.trim().length() == 0;
        if (!isEmpty) {
            return "redirect:/";
        }
        // ***********************************************
        // если нет записи создать месячный отчет
        DateTimeFormatter format_end = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate nowend = LocalDate.now();
         endDate = nowend.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(format_end);


        String rubinWeekMounth = rubinWeekRepository.setSumWeek(start_Date, end_Date, tsc_front);
        rubinWeekMounth = rubinWeekMounth + ',' + "(станом за " + month_year + ")," + pidrozdilRepository.setEmailPidrozdil(tsc_front);
        //  формируем запись для таблицы - rubin_month
        String[] str = rubinWeekMounth.split(",");
        Rubin_month rubin_month = new Rubin_month();//  Записать месячніе данные в таблицу Rubin_month
        rubin_month.setMonth_appeal(Integer.parseInt(str[0]));
        rubin_month.setMonth_issued(Integer.parseInt(str[1]));
        rubin_month.setPidrozdil(tsc_front);
        rubin_month.setData_v(end_Date);
        rubin_month.setMonth_year(endDate.substring(3));
        monthRepository.save(rubin_month);  //  Запись в месячную таблицу

//      Внестии в ексел
        try {
            CreateExilMonth.CreateMonth(rubinWeekMounth);//  Внесение полученной информации в ексл и отправка его на почту
        } catch (IOException e) {
            e.printStackTrace();
        }
//      И отправить юзеру  на почту

        EmailSender.send(str[4]);
//
        model.addAttribute("rubinList", rubinWeekMounth);

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findAll();
        model.addAttribute("pidrozdilList", pidrozdilList);

        model.addAttribute("dat", startDate);
        model.addAttribute("dat_last", endDate);
//        String mod = model.toString();
        return "redirect:/";
    }

    @GetMapping("/rubin/week/rubin-add-week")
    public String rubinAddWeek(HttpServletRequest request, Model model) {

        String ip_user = request.getRemoteAddr();//        вытягивает IP копма с которого вносят информацию
//        ip_user="172.0.0.0";
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);

        String ip = ip_user.substring(0, end);//        узнаел подсеть

        String tsc = pidrozdilRepository.setNamePidrozdil(ip);//        по подсети узнаем из какого ТСЦ зашли работать

        boolean isEmpty = tsc == null || tsc.trim().length() == 0;
        if (isEmpty) {
            return "redirect:/";
        }

        Date date = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();//        //Getting the default zone id
        Instant instant = date.toInstant();//Converting the date to Instant

        Rubin_week rubin_week = new Rubin_week();
        rubin_week.setPidrozdil(pidrozdilRepository.setNamePidrozdil(ip));
        rubin_week.setData_v(instant.atZone(defaultZoneId).toLocalDate());

        model.addAttribute("rubin_week", rubin_week);
        return "rubin/week/rubin-add-week";
    }

    @PostMapping("/rubin/week/rubin-add-week")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String rubinAddWeek(@RequestParam String pidrozdil,
                               @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data_v,
                               @RequestParam int week_issued,
                               @RequestParam int week_appeal) {
//        Rubin_week rubin_week = new Rubin_week(pidrozdil, data_v,week_appeal, week_issued );
        Rubin_week rubin_week = new Rubin_week();
        rubin_week.setPidrozdil(pidrozdil);
        rubin_week.setData_v(data_v);
        rubin_week.setWeek_appeal(week_appeal);
        rubin_week.setWeek_issued(week_issued);
        rubinWeekRepository.save(rubin_week);
        return "redirect:/";
    }

    @GetMapping("/rubin/week/{id}")
    public String rubinWeekDetails(Model model, @PathVariable("id") long id) {

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
                              @RequestParam String pidrozdil,
                              @RequestParam int week_appeal,
                              @RequestParam int week_issued,
                              @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v
    ) {
        Rubin_week rubin_week_up = rubinWeekRepository.findById(id).orElseThrow();
        rubin_week_up.setPidrozdil(pidrozdil);
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
            data_v = sdf2.parse("2021-11-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    Rubin_month rubinMonth = new Rubin_month();

    rubinMonth.setData_v(data_v);
    rubinMonth.setMonth_appeal(133);
    rubinMonth.setMonth_issued(115);
    rubinMonth.setMonth_year("11.2021");
    rubinMonth.setPidrozdil("ТСЦ 1843");
    monthRepository.save(rubinMonth);
        return "redirect:/";

    }

}
