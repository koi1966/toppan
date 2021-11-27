package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.createDoc.CreateDoc;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.models.Rubin;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinRepository;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

@Controller
//@RequestMapping("/rubin")
public class RubinController {

    private final RubinRepository rubinRepository;
    private final PidrozdilRepository pidrozdilRepository;

    private final CreateExcel createExcel;
    private CreateDoc createDoc;

    public RubinController(RubinRepository rubinRepository, PidrozdilRepository pidrozdilRepository, CreateExcel createExcel) {
        this.rubinRepository = rubinRepository;
        this.pidrozdilRepository = pidrozdilRepository;
        this.createExcel = createExcel;
    }

    @GetMapping("/rubin/rubin-view")
    public String rubinview(Model model) {
//      находим и передаем все записи на вюшку

//        List<Rubin> rubinList = (List<Rubin>) rubinRepository.findAll();
        String date_s = LocalDate.now().toString();

        Date data_v= null;
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

        return "/rubin/rubin-view-p";
    }

    @PostMapping("/rubin/rubin-add")
//    public String rubinadd(@RequestParam String pidrozdil,
//                           @RequestParam int week,
//                           @RequestParam int week_1,
//                           @RequestParam int year,
//                           @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v) {
    public String rubinadd(@Valid Rubin rubin, BindingResult bindingResult) {
//        public String rubinadd(Rubin rubin) {
        if (bindingResult.hasErrors())
            return "/rubin/rubin-add";

//        Rubin rubin = new Rubin(pidrozdil, week, week_1, year_0, year_1, data_v);
        rubinRepository.save(rubin);
        final String s = "redirect:/rubin/rubin-view";
        return s;
    }

    @GetMapping("/rubin/rubin-add")
    public String rubinadd(HttpServletRequest request, Model model) {
//        вытягивает IP копма с которого вносят информацию
        String ip_user = request.getRemoteAddr();
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

        Rubin rubin = new Rubin();
        rubin.setPidrozdil(pidrozdilRepository.setNamePidrozdil(ip));
        rubin.setData_v(instant.atZone(defaultZoneId).toLocalDate());

        model.addAttribute("rubin", rubin);

        return "/rubin/rubin-add";
    }

    @PostMapping("/rubin/rubin-view-p")
    public String rubinViewP(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v,  Model model) {
//        model.getAttribute();

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Текущая дата " + formatForDateNow.format(data_v));
        String rubinStr= rubinRepository.setSumDate(data_v);
        model.addAttribute("dat", data_v);

        try {
            createExcel.CreateF(rubinStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String s = "redirect:/rubin/rubin-view";
        return s;
    }

    @GetMapping("/rubin/{id}")
    public String rubinDetaіls(Model model, @PathVariable(value = "id") long id){
      //      находим и передаем єту одну запись на вюшку
        Optional<Rubin> rubin = rubinRepository.findById(id);
        ArrayList<Rubin> res = new ArrayList<>();

        rubin.ifPresent(res::add);
        model.addAttribute("rubin", res);

//       отображаем найденное на вюшке
        return "/rubin/rubin-deteils";
    }

    @PatchMapping("/rubin/{id}/edit")
    public String update(@ModelAttribute("rubin") Rubin rubin){
        rubinRepository.save(rubin);
        final String s = "redirect:/rubin/rubin-view";
        return s;
    }

    @GetMapping("/rubin/{id}/update") // после НАЖАТИЯ НА кнопкУ редактирование даннЫх
    public String rubinEdit(Model model, @PathVariable(value = "id") long id){
        //      находим и передаем єту одну запись на вюшку
        Optional<Rubin> rubin = rubinRepository.findById(id);
        ArrayList<Rubin> res = new ArrayList<>();

        rubin.ifPresent(res::add);
        model.addAttribute("rubin", res);
//       редактируем найденное на вюшке
        return "/rubin/rubin-edit";
    }


    @PostMapping("/rubin/{id}/update")   // после нажатия Зберегти зміни
    public String rubinUpdate(  Model model,
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

}




