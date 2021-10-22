package toppan.example.toppan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.models.repo.ToppanRepository;
import toppan.example.toppan.models.Toppan;

import java.util.List;

@Controller
public class ToppanController {

    @Autowired
    private ToppanRepository toppanRepository;

    @GetMapping("/toppan")
    public String Toppan(Model model) {
//      находим и передаем все записи на вюшку
        List<Toppan> toppanList = (List<Toppan>) toppanRepository.findAll();
        model.addAttribute("toppan", toppanList);
        return "toppan";
    }



    @PostMapping("/")
    public String addToppan(Model model) {
//      находим и передаем все записи на вюшку
        List<Toppan> toppanList = (List<Toppan>) toppanRepository.findAll();
        model.addAttribute("toppan", toppanList);
        return "toppan-add";
    }

    @PostMapping("/toppan-add")
    public String addToppan(@RequestParam String sn, @RequestParam String datawork ,@RequestParam String completeness, @RequestParam String code, Model model) {
        Toppan top = new Toppan(sn, datawork, completeness, code);
        //  request.getRemoteAddr()  -  вытягивает IP копма с которого вносят информацию
        toppanRepository.save(top);
//        openTransactionSession();

//        repository.toString();
        return "redirect:/toppan";
    }

    @GetMapping("/toppan-add")
    public String Toppanadd(Model model) {
        return "toppan-add";
    }

}
