package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toppan.example.toppan.mapper.Mapper;
import toppan.example.toppan.models.Arest;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.dto.KartaDTO;
import toppan.example.toppan.service.ArestDAO;
import toppan.example.toppan.service.KartaDAO;
import toppan.example.toppan.service.KartaDAOSybase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/karta")
public class KartaController {
    private final KartaDAO kartaDAO;
    private final KartaDAOSybase kartaDAOSybase;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public KartaController(KartaDAO kartaDAO, KartaDAOSybase kartaDAOSybase) {
        this.kartaDAO = kartaDAO;
        this.kartaDAOSybase = kartaDAOSybase;
    }

    @GetMapping("/searchAMT")
    public String searchAMT(@ModelAttribute("karta") Karta karta) {

        return "karta/searchAMT";
    }

    // Получить с html формы поля для обработки @PostMapping
    @PostMapping()
    public String search(@ModelAttribute("karta") Karta kar, @ModelAttribute("lastOper") String check, Model model) {
        log.info("All users age > {}", check);
        final List<Karta> kartaAMTList = kartaDAO.search(kar,check);
        model.addAttribute("kartaList", kartaAMTList);
        return "karta/viewKarta";
    }

    @GetMapping("/search")
    public List<KartaDTO> searchKarta(@RequestParam String znak) {
        log.info("All records, searched by znak > {}", znak);
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
                    .SearchArest(kart_id); // search the arrest table ( arest )  Search_Arest by kart_id
            model.addAttribute("arest", ArestA); // transfer the found arrest to viewing
        }

        model.addAttribute("Amthystory", AMTHys);

        return "karta/historearest";
    }

    @GetMapping("/test")
    public String testKarta() {
        log.info("See html test !!!");

        return "karta/test";
    }

    @GetMapping("/searchKarta")
    public String testKarta2() {
        log.info("See is test !!!");

        return "karta/searchKarta";
    }

    @GetMapping("/a")
    public String migrationArest() throws SQLException {
//        final List<ArestSybase> arestSybase = kartaDAOSybase.searchArest(, );
//    System.out.println(karta.toString());
        return null;
    }

//    @PostMapping(value = "/arest", params = "action=print_month")
    @PostMapping(value = "/arestupdate",params = "action=update_arest")
    public String arestupdate(@RequestParam("data_first") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                              @RequestParam("data_last") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
                                  ) throws SQLException {
        log.info("Generate report , date from: {}, to: {}", from, to);
        kartaDAOSybase.updateArest(from, to);
        return "redirect:/";
    }
    @PostMapping(value = "/arestupdate",params = "action=compare_arest")
    public String arestupdate() throws SQLException {
//  compare table arest in database Sybase and PostgreSql
        kartaDAOSybase.checArest();
        return "redirect:/";
    }
}
