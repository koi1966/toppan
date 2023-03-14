package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.createDoc.CreateExcel;
import toppan.example.toppan.models.Toppan;
import toppan.example.toppan.models.repo.ToppanRepository;

import java.util.List;

@Slf4j   // Логер
@Controller
public class ToppanController {
    @Autowired
    private ToppanRepository toppanRepository;

    @Autowired
    private CreateExcel createExcel;

    @GetMapping("/printer/toppan")
    public String Toppan(Model model) {
//      find and transfer all records to front
        List<Toppan> toppanList = (List<Toppan>) toppanRepository.findAll();
        model.addAttribute("toppan", toppanList);

        return "printer/toppan";
    }

    @PostMapping("/printer/toppan-add")
    public String addToppan(@RequestParam String sn, @RequestParam String datawork, @RequestParam String completeness, @RequestParam String code, @RequestParam String pidrozdil, Model model) {
        Toppan top = new Toppan(sn, datawork, completeness, code, pidrozdil);
        //  request.getRemoteAddr()  -  GET IP user computer - from which information is entered
        toppanRepository.save(top);

        return "redirect:/printer/toppan";
    }

    @GetMapping("/printer/toppan-add")
    public String Toppanadd(Model model) {
        return "printer/toppan-add";
    }

    @GetMapping("/printer/Total_Count")
    public String TotalCountAdd(Model model) {
        return "printer/Total_Count";
    }

}
