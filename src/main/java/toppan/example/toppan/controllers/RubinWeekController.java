package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.models.Pidrozdil;
import toppan.example.toppan.models.Rubin_week;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
class RubinWeekController {

    private final RubinWeekRepository rubinWeekRepository;
    private final PidrozdilRepository pidrozdilRepository;
    private final CreateExcel createExcel;

    public RubinWeekController(RubinWeekRepository rubinWeekRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel) {
        this.rubinWeekRepository = rubinWeekRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.createExcel = createExcel;
    }

    @GetMapping("/rubin/week/rubin-week-view")
    public String rubinWeekViewAll(Model model) {
//      находим и передаем все записи на вюшку
        List<Rubin_week> rubinWeekList = (List<Rubin_week>) rubinWeekRepository.findAll();
        model.addAttribute("rubinList", rubinWeekList);
//        Берем локальную дату
        String date_s = LocalDate.now().toString(); // берем локальную дату переводим в String

        Date data_v = null;
        try {
            data_v = new SimpleDateFormat("yyyy-MM-dd").parse(date_s);  //  Локальная дата без времени
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findAll();
        model.addAttribute("pidrozdilList", pidrozdilList);

        model.addAttribute("dat", date_s);
        model.addAttribute("dat_last", date_s);
        final String s = "rubin/week/rubin-week-view";
        return s;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping("/rubin/week/rubin-week-view")
    public String rubinViewData(@RequestParam("data_v") String data_s,
                                @RequestParam("data_vpo") String data_last_str,
                                @RequestParam("p_tsc") String tsc,
                                @RequestParam(value="action", required=true) String action,
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

//        boolean isEmpty = tsc == null || tsc.trim().length() == 0;
//        if (isEmpty) {
//            List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) rubinWeekRepository.findAll();
//        }

        List<Pidrozdil> pidrozdilList = (List<Pidrozdil>) pidrozdilRepository.findAll();
        model.addAttribute("pidrozdilList", pidrozdilList);

        List<Rubin_week> rubinList = rubinWeekRepository.setListDateRubinWeek(data_v, data_last, tsc);

        model.addAttribute("rubinList", rubinList);
        model.addAttribute("dat", data_s);
        model.addAttribute("dat_last", data_last_str);
        final String s = "rubin/week/rubin-week-view";
        return s;
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
//        final String s= ;
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
//        final String s = "redirect:rubin/week/rubin-week-view";redirect:/
//        return s;
    }

    //    @GetMapping(value = "/rubin/week/{id}")
    @GetMapping("/rubin/week/{id}")
    public String rubinWeekDetails(Model model, @PathVariable("id") long id) {

        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin_week> rubin = rubinWeekRepository.findById(id);
        ArrayList<Rubin_week> res = new ArrayList<>();

        rubin.ifPresent(res::add);
        model.addAttribute("rubin_week_det", res);

//       отображаем найденное на вюшке
        final String s="rubin/week/rubin-week-deteils";
        return s;
    }

    //    @PatchMapping("/rubin/week/{id}/edit")
    @PatchMapping("/rubin/week/{id}/edit")
    public String update(@ModelAttribute("rubin") Rubin_week rubin_week) {
        rubinWeekRepository.save(rubin_week);
        final String s = "redirect:rubin/week/rubin-week-view";
        return s;
    }

    //    @GetMapping("/rubin/week/{id}/update") // после НАЖАТИЯ НА кнопкУ редактирование даннЫх
    @GetMapping("/rubin/week/{id}/update")
    public String rubinEdit(Model model, @PathVariable(value = "id") long id) {
        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin_week> rubin_week = rubinWeekRepository.findById(id);
        ArrayList<Rubin_week> res = new ArrayList<>();

        rubin_week.ifPresent(res::add);
        model.addAttribute("rubin_week_edit", res);
//       редактируем найденное на вюшке

        final String s="rubin/week/rubin-week-edit";
        return s;
    }

    //    @PostMapping("/rubin/week/{id}/update")   // после нажатия Зберегти зміни
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
        final String s = "redirect:rubin/week/rubin-week-view";
        return s;
    }


    @PostMapping("/rubin/week/rubin-print-week")
    public String rubinWeekPrint(@RequestParam("data_v") String data_s,
                                 @RequestParam("data_vpo") String data_last_str,
                                 @RequestParam("p_tsc") String tsc,
                                 Model model) {
//        model.getAttribute();

//************************************************************
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Текущая дата " + formatForDateNow.format(data_v));
//        String rubinStr = rubinRepository.setSumDate(data_v);
//        model.addAttribute("dat", data_v);
//  Внесение полученной информации в ексл и отправка его на почту
//        try {
//            createExcel.CreateF(rubinStr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        EmailSender.send();
//************************************************************
        final String s = "redirect:/rubin/week/rubin-view";
        return s;
    }
}
