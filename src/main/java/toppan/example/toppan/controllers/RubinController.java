package toppan.example.toppan.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.models.Rubin;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinRepository;
import toppan.example.toppan.utilities.UtilitesSting;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class RubinController {

    private RubinRepository rubinRepository;
    private final PidrozdilRepository pidrozdilRepository;

    public RubinController(RubinRepository rubinRepository, PidrozdilRepository pidrozdilRepository) {
        this.rubinRepository = rubinRepository;
        this.pidrozdilRepository = pidrozdilRepository;
    }

    @GetMapping("/rubin/rubin-view")
    public String rubinview(Model model) {
//      находим и передаем все записи на вюшку
        List<Rubin> rubinList = (List<Rubin>) rubinRepository.findAll();
        model.addAttribute("rubinList", rubinList);
        return "rubin/rubin-view";
    }

    @PostMapping("/rubin/rubin-view")
    public String rubinViewData(@RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v, Model model) {

        List<Rubin> rubinList = (List<Rubin>) rubinRepository.setListDateRubin(data_v);
        model.addAttribute("rubinList", rubinList);

        return "rubin/rubin-view";
    }


//    @PostMapping("/rubin/rubin-add")
////    public String rubinadd(@RequestParam String pidrozdil, @RequestParam int week, @RequestParam int week_1, @RequestParam int year, @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v) {
//    public <rubin> String rubinadd(@Valid Rubin rubin) {
////        rubinRepository.save(rubin);
////        return "redirect:/rubin/rubin-view";
//        return rubinadd(rubin.getPidrozdil(), rubin.getWeek(), rubin.getWeek_1(), rubin.getYear(), rubin.getYear_1(), rubin.getData_v());
//    }


//    @PostMapping("/rubin/rubin-add")
////    public String rubinadd(@RequestParam String pidrozdil, @RequestParam int week, @RequestParam int week_1, @RequestParam int year, @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v) {
//    public String rubinadd(@RequestParam String pidrozdil,
//                                   @RequestParam int week,
//                                   @RequestParam int week_1,
//                                   @RequestParam int year,
//                                   @RequestParam int year_1,
//                                   @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v ) {
//        //       String ip_user = request.getRemoteAddr();
////       String host = request.getRemoteHost();
//    Rubin rubin = new Rubin(pidrozdil, week, week_1, year, year_1, data_v);
////        Rubin rubin = new Rubin();
//        //  request.getRemoteAddr()  -  вытягивает IP копма с которого вносят информацию
//        rubinRepository.save(rubin);
//        return "redirect:/rubin/rubin-view";
//    }

    @PostMapping("/rubin/rubin-add")
    public String rubinadd(@RequestParam String pidrozdil, @RequestParam int week, @RequestParam int week_1, @RequestParam int year, @RequestParam int year_1, @RequestParam("data_v") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data_v) {
//       String ip_user = request.getRemoteAddr();
//       String host = request.getRemoteHost();
        Rubin rubin = new Rubin(pidrozdil, week, week_1, year, year_1, data_v);
        //  request.getRemoteAddr()  -  вытягивает IP копма с которого вносят информацию
        rubinRepository.save(rubin);
        return "redirect:/rubin/rubin-view";
    }


    @GetMapping("/rubin/rubin-add")
    public String rubinadd(HttpServletRequest request, Model model) {
//        вытягивает IP копма с которого вносят информацию
        String ip_user = request.getRemoteAddr();

//        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
//        int start = 0;
        int end = UtilitesSting.ordinalIndexOf(ip_user, ".", 2);
//        char[] dst=new char[end - start];
//        ip_user.getChars(start, end,dst, 0);
//        узнаеп подсеть
        String ip = ip_user.substring(0, end);
//        по подсети узнаем из какого ТСЦ зашли работать
        String tsc = pidrozdilRepository.setNamePidrozdil(ip);
//        tscs.add(tsc);
        boolean isEmpty = tsc == null || tsc.trim().length() == 0;
        if (isEmpty) {
            return "redirect:/";
        }
        model.addAttribute("tsc", pidrozdilRepository.setNamePidrozdil(ip));
        return "rubin/rubin-add";
    }

    @GetMapping("/rubin/rubin")
    public String rubin_Test(HttpServletRequest request, Model model) {
//        String ip_user = request.getRemoteAddr();
//        String tsc = pidrozdilRepository.setNamePidrozdil(request.getRemoteAddr());
        model.addAttribute("tsc", pidrozdilRepository.setNamePidrozdil(request.getRemoteAddr()));
        return "rubin/rubin";
    }

}




