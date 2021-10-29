package toppan.example.toppan.controllers;

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

@Controller
public class ToppanController {
    @Autowired
    private ToppanRepository toppanRepository;

    @Autowired
    private CreateExcel createExcel;

    @GetMapping("/printer/toppan")
    public String Toppan(Model model) {
//      находим и передаем все записи на вюшку
        List<Toppan> toppanList = (List<Toppan>) toppanRepository.findAll();
        model.addAttribute("toppan", toppanList);
        createExcel.
        return "printer/toppan";
    }

    @PostMapping("/printer/toppan-add")
    public String addToppan(@RequestParam String sn, @RequestParam String datawork ,@RequestParam String completeness, @RequestParam String code, @RequestParam String pidrozdil, Model model) {
        Toppan top = new Toppan(sn, datawork, completeness, code, pidrozdil);
        //  request.getRemoteAddr()  -  вытягивает IP копма с которого вносят информацию
        toppanRepository.save(top);
//        openTransactionSession();

//        repository.toString();
        return "redirect:/printer/toppan";
    }

    @GetMapping("/printer/toppan-add")
    public String Toppanadd(Model model) {
        return "printer/toppan-add";
    }

}
