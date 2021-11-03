package toppan.example.toppan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.models.Rubin;
import toppan.example.toppan.models.repo.PidrozdilRepository;
import toppan.example.toppan.models.repo.RubinRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @PostMapping("/rubin/rubin-add")
    public String rubin_add(@RequestParam String pidrozdil,@RequestParam int week, @RequestParam int week_1, @RequestParam int year, @RequestParam int year_1,  @RequestParam Date data_v, HttpServletRequest request,Model model) {
//       String ip_user = request.getRemoteAddr();
//       String host = request.getRemoteHost();
        Rubin rubin = new Rubin(pidrozdil,week,week_1, year, year_1, data_v);
        //  request.getRemoteAddr()  -  вытягивает IP копма с которого вносят информацию
        rubinRepository.save(rubin);
//        openTransactionSession();

//        repository.toString();
        return "redirect:/rubin/rubin-view";
    }

    @GetMapping("/rubin/rubin-add")
    public String rubin_add(HttpServletRequest request,Model model) {
        String ip_user = request.getRemoteAddr();
//        List<RubinP> tscP =
        String tsc = pidrozdilRepository.setNamePidrozdil(ip_user);
        ArrayList<String> tscs = new ArrayList<>(); // выделли свободную память " res = new ArrayList<>()" ;
        tscs.add(tsc);
        model.addAttribute("tsс", tscs);

//        rubinRepository.toString();
        return "rubin/rubin-add";
    }

}




