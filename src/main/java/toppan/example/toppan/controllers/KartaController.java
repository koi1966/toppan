package toppan.example.toppan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.models.Arest;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.service.ArestDAO;
import toppan.example.toppan.service.KartaDAO;

import java.util.List;

@Controller
@RequestMapping("/karta")
public class KartaController {
    private final KartaDAO kartaDAO;

    public KartaController(KartaDAO kartaDAO) {
        this.kartaDAO = kartaDAO;
    }

    @GetMapping("/searchAMT")
    public String searchAMT(@ModelAttribute("karta") Karta karta, Model model) {

        return "karta/searchAMT";
    }

    // Получить с html формы поля для обработки @PostMapping
    @PostMapping()
    public String search(@ModelAttribute("karta") Karta kar, Model model) {
        final List<Karta> kartaAMTList = kartaDAO.search(kar);
        model.addAttribute("kartaList", kartaAMTList);
        return "karta/viewKarta";
    }

    @GetMapping("/{id}")
    public String AmtHystory(@PathVariable("id") long id, Model model) {
        List<Karta> AMTHys = kartaDAO.AmtHistory(id);
  //      System.out.println(AMTHys.get(0));
        //    String kart_id1 = AMTHys.get(0).getKart_id();
        if (!AMTHys.isEmpty()) {
            Karta kartaAMT = AMTHys.get(0);
            String kart_id = kartaAMT.getKart_id();

            List<Arest> ArestA = new ArestDAO()
                    .Serch_Arest(kart_id); // реализовать поиск по арестам Serch_Arest по kart_id
            model.addAttribute("arest", ArestA); // передать найденый арест на вьюшку
        }

        model.addAttribute("Amthystory", AMTHys);

        return "karta/historearest";
    }

}