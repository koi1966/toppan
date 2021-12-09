package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.models.Pidrozdil;
import toppan.example.toppan.models.Rubin;
import toppan.example.toppan.models.Rubin_week;
import toppan.example.toppan.models.Rubin_year;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;
import toppan.example.toppan.models.repo.RubinYearRepository;
import toppan.example.toppan.utilities.EmailSender;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public RubinWeekController(RubinWeekRepository rubinWeekRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel, RubinYearRepository rubinYearRepository, RubinRepository rubinRepository) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.createExcel = createExcel;
        this.rubinYearRepository = rubinYearRepository;
        this.rubinRepository = rubinRepository;
    }

    @GetMapping("/rubin/week/rubin-week-view")
    public String rubinWeekViewAll(HttpServletRequest request, Model model) throws ParseException {

        final LocalDate today = LocalDate.now();  // берем сегдняшнюю дату
//        final LocalDate nextSunday = today.with(next(SUNDAY)); // берем будущее воскресенье
        final LocalDate thisPastSunday = today.with(previous(SUNDAY));  // берем прошедшее воскресенье
        //***********************************
//        LocalDate dat_firs_t = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());  //        Берем первое число текущего месяца
        String dat_first = thisPastSunday.toString();
//  dat_first - переводим в тип Date  первое число месяца
        Date dat_f = new SimpleDateFormat("yyyy-MM-dd").parse(dat_first);

        String date_s = LocalDate.now().toString(); // берем локальную дату переводим в String

        Date data_v = null;
        try {
            data_v = new SimpleDateFormat("yyyy-MM-dd").parse(date_s);  //  Локальная дата без времени
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //***********************************
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
//                                @RequestParam(value="action", required=true) String action,
                                Model model) {

        //************************************
//        final LocalDate today = LocalDate.now();
//        final LocalDate nextSunday = today.with(next(SUNDAY));
//        final LocalDate thisPastSunday = today.with(previous(SUNDAY));
        //***********************************
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
    public String rubinViewDataPrint(@RequestParam("data_v") String data_s,
                                     @RequestParam("data_vpo") String data_last_str,
                                     @RequestParam("p_tsc") String tsc,
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

        String rubinWekStr = rubinWeekRepository.setWeekPrint(data_v, data_last, tsc);
        rubinWekStr = rubinWekStr + ',' + "(станом на " + data_last_str + ")," + tsc + ',' + pidrozdilRepository.setEmailPidrozdil(tsc);
        //  *****************************************************************************
        //  Внесение полученной информации в ексл и отправка его на почту
        try {
            createExcel.CreateF(rubinWekStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] str = rubinWekStr.split(",");
        EmailSender.send(str[6]);
        //  Запись сформированного отчета по таблицам
        Rubin rubin = new Rubin();
        rubin.setPidrozdil(tsc);

        LocalDate dat_f = LocalDate.parse(data_last_str);
//        Date dat_f =new SimpleDateFormat("dd/MM/yyyy").parse(data_last_str);
        rubin.setData_v(dat_f);

        rubin.setWeek(Integer.parseInt(str[0]));
        rubin.setWeek_1(Integer.parseInt(str[1]));

        rubin.setYear_0(Integer.parseInt(str[2]));
        rubin.setYear_1(Integer.parseInt(str[3]));
        rubinRepository.save(rubin);

        //  Записать недельные данные в таблицу rubin_year
        Rubin_year rubin_year = new Rubin_year();
        rubin_year.setPidrozdil(tsc);
        rubin_year.setData_v(data_last);
        rubin_year.setYear_appeal(Integer.parseInt(str[2]));
        rubin_year.setYear_issued(Integer.parseInt(str[3]));
        rubinYearRepository.save(rubin_year);  //  Запист в годовую таблицю
        return "rubin/week/rubin-week-view";
    }

    @GetMapping("/rubin/week/rubin-add-week")
    public String rubinAddWeek(HttpServletRequest request, Model model) {
//        вытягивает IP копма с которого вносят информацию
        String ip_user = request.getRemoteAddr();
//        ip_user="172.0.0.0";
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);

//        узнаеп подсеть
        String ip = ip_user.substring(0, end);

//        по подсети узнаем из какого ТСЦ зашли работать
        String tsc = pidrozdilRepository.setNamePidrozdil(ip);

        boolean isEmpty = tsc == null || tsc.trim().length() == 0;
        if (isEmpty) {
            return "redirect:/";
        }

        Date date = new Date();
//        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //Converting the date to Instant
        Instant instant = date.toInstant();

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

        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin_week> rubin = rubinWeekRepository.findById(id);
        ArrayList<Rubin_week> res = new ArrayList<>();

        rubin.ifPresent(res::add);
        model.addAttribute("rubin_week_det", res);
//       отображаем найденное на вюшке
        return "rubin/week/rubin-week-deteils";
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
//       редактируем найденное на вюшке
        return "rubin/week/rubin-week-edit";
    }

    @PostMapping("/rubin/week/{id}/update")
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
        return "redirect:rubin/week/rubin-week-view";
    }

}
