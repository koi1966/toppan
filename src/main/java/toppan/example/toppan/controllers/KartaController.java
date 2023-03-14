package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.mapper.Mapper;
import toppan.example.toppan.models.Arest;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.dto.KartaDTO;
import toppan.example.toppan.service.ArestDAO;
import toppan.example.toppan.service.KartaDAO;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/karta")
public class KartaController {
    private final KartaDAO kartaDAO;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public KartaController(KartaDAO kartaDAO) {
        this.kartaDAO = kartaDAO;
    }

    @GetMapping("/searchAMT")
    public String searchAMT(@ModelAttribute("karta") Karta karta) {

        return "karta/searchAMT";
    }

    // Получить с html формы поля для обработки @PostMapping
    @PostMapping()
    public String search(@ModelAttribute("karta") Karta kar, @ModelAttribute("lastOper") String check, Model model) {

        final List<Karta> kartaAMTList = kartaDAO.search(kar,check);
        model.addAttribute("kartaList", kartaAMTList);
        return "karta/viewKarta";
    }

    @GetMapping("/search")
    public List<KartaDTO> searchKarta(@RequestParam String znak) {
        log.info("All users age > {}", znak);
        List<Karta> karta = kartaDAO.kartaList(znak);
        return mapper.mapKartaToKartaDto(karta);
    }

    @GetMapping("/{id}")
    public String AmtHystory(@PathVariable("id") long id, Model model) {
        List<Karta> AMTHys = kartaDAO.AmtHistory(id);

        if (!AMTHys.isEmpty()) {
            Karta kartaAMT = AMTHys.get(0);
            String kart_id = kartaAMT.getKart_id();

            List<Arest> ArestA = new ArestDAO()
                    .Serch_Arest(kart_id); // search the arrest table ( arest )  Serch_Arest by kart_id
            model.addAttribute("arest", ArestA); // transfer the found arrest to viewing
        }

        model.addAttribute("Amthystory", AMTHys);

        return "karta/historearest";
    }

//    Test

    @GetMapping("/test")
    public String testKarta() {
        log.info("See html test !!!");

        return "karta/test";
    }

    @GetMapping("/searchKarta")
    public String testKarta2() {
        log.info("See js test !!!");

        return "karta/searchKarta";
    }

//    searchKarta.html


}
