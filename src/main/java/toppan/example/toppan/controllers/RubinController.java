package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.createDoc.CreateExilMonth;
import toppan.example.toppan.models.Rubin;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinRepository;
import toppan.example.toppan.models.repo.RubinWeekRepository;
import toppan.example.toppan.utilities.EmailSender;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

@Controller
//@RequestMapping("/rubin")
public class RubinController {

    private final RubinRepository rubinRepository;
    private final PidrozdilRepository pidrozdilRepository;
    private final RubinWeekRepository rubinWeekRepository;
    private final CreateExcel createExcel;
    private final CreateExilMonth createExilMonth;

    public RubinController(RubinRepository rubinRepository, PidrozdilRepository pidrozdilRepository, RubinWeekRepository rubinWeekRepository, CreateExcel createExcel, CreateExilMonth createExilMonth) {
        this.rubinRepository = rubinRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.rubinWeekRepository = rubinWeekRepository;
        this.createExcel = createExcel;
        this.createExilMonth = createExilMonth;
    }

    @GetMapping("/rubin/rubin-view")
    public String rubinview(Model model) {
//      находим и передаем все записи на вюшку
//        List<Rubin> rubinList = (List<Rubin>) rubinRepository.findAll();
        String date_s = LocalDate.now().toString(); // берем локальную дату переводим в String

        Date data_v = null;
        try {
            data_v = new SimpleDateFormat("yyyy-MM-dd").parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rubin> rubinList = (List<Rubin>) rubinRepository.setListDateRubin(data_v);
        model.addAttribute("rubinList", rubinList);
        model.addAttribute("dat", data_v);
        return "rubin/rubin-view";
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PostMapping("/rubin/rubin-view")
    public String rubinViewData(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v, Model model) {

        List<Rubin> rubinList = (List<Rubin>) rubinRepository.setListDateRubin(data_v);
        model.addAttribute("rubinList", rubinList);
        model.addAttribute("dat", data_v);
        return "rubin/rubin-view-p";
    }

    @PostMapping("/rubin/rubin-add")
//    public String rubinadd(@RequestParam String pidrozdil,
//                           @RequestParam int week,
//                           @RequestParam int week_1,
//                           @RequestParam int year,
//                           @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v) {
    public String rubinadd(@Valid Rubin rubin, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "rubin/rubin-add";
        rubinRepository.save(rubin);
        return "redirect:/rubin/rubin-view";
    }

    @GetMapping("/rubin/rubin-add")
    public String rubinadd(HttpServletRequest request, Model model) {
        String ip_user = request.getRemoteAddr();//        вытягивает IP копма с которого вносят информацию
        int ip_tsc = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
        String ip = ip_user.substring(0, ip_tsc);//        узнаеп подсеть
        String tsc = pidrozdilRepository.setNamePidrozdil(ip);//        по подсети узнаем из какого ТСЦ зашли работать

        boolean isEmpty = tsc == null || tsc.trim().length() == 0;
        if (isEmpty) {
            return "redirect:/";
        }

        Date date = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();//        //Getting the default zone id
        Instant instant = date.toInstant();        //Converting the date to Instant

        Rubin rubin = new Rubin();
        rubin.setPidrozdil(pidrozdilRepository.setNamePidrozdil(ip));
        rubin.setData_v(instant.atZone(defaultZoneId).toLocalDate());
        model.addAttribute("rubin", rubin);
        return "rubin/rubin-add";
    }

    @PostMapping("/rubin/rubin-view-p")
    public String rubinViewP(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v, Model model) {

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        String rubinStr = rubinRepository.setSumDate(data_v);

//        rubinStr = rubinStr + ',' + "(станом на " + data_last_str + ")," + tsc + ',' + pidrozdilRepository.setEmailPidrozdil(tsc);
        rubinStr = rubinStr + ',' + "(станом на " + LocalDate.now().toString() + ")";

        model.addAttribute("dat", data_v);
        try {
            createExcel.CreateF(rubinStr);//  Внесение полученной информации в ексл и отправка его на почту
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmailSender.send("it@zhi.hsc.gov.ua");
        return "redirect:/rubin/rubin-view";
    }

    @GetMapping("/rubin/{id}")
    public String rubinDetaіls(Model model, @PathVariable(value = "id") long id) {
        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin> rubin = rubinRepository.findById(id);
        ArrayList<Rubin> res = new ArrayList<>();
        rubin.ifPresent(res::add);
        model.addAttribute("rubin", res);
        return "rubin/rubin-deteils";//       отображаем найденное на вюшке
    }

    @PatchMapping("/rubin/{id}/edit")
    public String update(@ModelAttribute("rubin") Rubin rubin) {
        rubinRepository.save(rubin);
        return "redirect:/rubin/rubin-view";
    }

    @GetMapping("/rubin/{id}/update") // после НАЖАТИЯ НА кнопкУ редактирование даннЫх
    public String rubinEdit(Model model, @PathVariable(value = "id") long id) {
        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin> rubin = rubinRepository.findById(id);
        ArrayList<Rubin> res = new ArrayList<>();
        rubin.ifPresent(res::add);
        model.addAttribute("rubin", res);
        return "rubin/rubin-edit";//       редактируем найденное на вюшке
    }


    @PostMapping("/rubin/{id}/update")   // после нажатия Зберегти зміни
    public String rubinUpdate(Model model,
                              @PathVariable(value = "id") long id,
                              @RequestParam String pidrozdil,
                              @RequestParam int week,
                              @RequestParam int week_1,
                              @RequestParam int year_0,
                              @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v
    ) {
        Rubin rubin = rubinRepository.findById(id).orElseThrow();
        rubin.setPidrozdil(pidrozdil);
        rubin.setWeek(week);
        rubin.setWeek_1(week_1);
        rubin.setYear_0(year_0);
        rubin.setYear_1(year_1);
        rubinRepository.save(rubin);
        return "redirect:/rubin/rubin-view";
    }

    @GetMapping("/rubin/rubin-view-month")
    public String rubinViewmonth(Model model) {


        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now = LocalDate.now();
        String startDate = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(format);
        String endDate = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(format);

//        Date start_Date= null;
//        try {
//            start_Date = new SimpleDateFormat("yyyy.MM.dd").parse(startDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "rubin/rubin-view-month";
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @PostMapping("/rubin/rubin-view-month")
    public String rubinMonthPrint(@RequestParam("dat_start") String dat_start,
                                  @RequestParam("dat_end") String dat_end) {

        Date date_start = null;
        try {
            date_start = new SimpleDateFormat("dd.MM.yyyy").parse(dat_start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date_end = null;
        try {
            date_end =new SimpleDateFormat("dd.MM.yyyy").parse(dat_end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String rubinStr = rubinWeekRepository.setRubinDate(date_start,date_end);

        LocalDate localDate = date_end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
//        int day   = localDate.getDayOfMonth();

       rubinStr = rubinStr + ',' + "(за " + String.valueOf(month)+"." + String.valueOf(year)+ " )";

//        model.addAttribute("dat", data_v);
        try {
            createExilMonth.CreateMonth(rubinStr);//  Внесение полученной информации в ексл и отправка его на почту
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmailSender.send("o.klymchuk@zhi.hsc.gov.ua");
        return "redirect:/";
    }
}




