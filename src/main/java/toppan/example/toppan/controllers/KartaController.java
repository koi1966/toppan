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
import toppan.example.toppan.models.repo.ArestRepository;
import toppan.example.toppan.service.ArestDAOPostgres;
import toppan.example.toppan.service.ArestDAOSybase;
import toppan.example.toppan.service.KartaDAO;
import toppan.example.toppan.service.KartaDAOSybase;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/karta")
public class KartaController {
    private final KartaDAO kartaDAO;
    private final KartaDAOSybase kartaDAOSybase;
    private final ArestDAOSybase arestDAOSybase;
    private final ArestRepository arestRepository;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public KartaController(KartaDAO kartaDAO, KartaDAOSybase kartaDAOSybase, ArestDAOSybase arestDAOSybase, ArestRepository arestRepository) {
        this.kartaDAO = kartaDAO;
        this.kartaDAOSybase = kartaDAOSybase;
        this.arestDAOSybase = arestDAOSybase;
        this.arestRepository = arestRepository;
    }

    @GetMapping("/searchAMT")
    public String searchAMT(@ModelAttribute("karta") Karta karta) {

        return "karta/searchAMT";
    }

    // Получить с html формы поля для обработки @PostMapping
    @PostMapping()
    public String search(@ModelAttribute("karta") Karta kar, @ModelAttribute("lastOper") String check, Model model) {
        log.info("All users age > {}", check);
        final List<Karta> kartaAMTList = kartaDAO.search(kar, check);
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

            List<Arest> ArestA = new ArestDAOPostgres()
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
    public String migrationArest() {
//        final List<ArestSybase> arestSybase = kartaDAOSybase.searchArest(, );
//    System.out.println(karta.toString());
        return null;
    }

    //    @PostMapping(value = "/arest", params = "action=print_month")
//    @PostMapping(value = "/arestupdate", params = "action=update_arest")
//    public String arestupdate(@RequestParam("data_first") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
//                              @RequestParam("data_last") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
//    ) throws SQLException {
//        log.info("Generate report , date from: {}, to: {}", from, to);
//        kartaDAOSybase.updateArest(from, to);
//        return "redirect:/";
//    }

    @GetMapping(value = "/arestupdate")
    public String arestupdate(Model model) throws SQLException {

        long recordPos = arestRepository.countDataSnaIsNotNull();
        long recordSyb = arestDAOSybase.countArestDataSna();
        long records = recordSyb - recordPos;
        Timestamp dataSnaPos = arestRepository.maxDataSnaArestPostgres();
        Timestamp dataSnaSybase = kartaDAOSybase.maxData_snaArestSybase();

        if (recordSyb != recordPos) {


        }

        if (!dataSnaSybase.equals(dataSnaPos)) {
            // call a method
            System.out.println(dataSnaSybase + "  > " + dataSnaPos + " = " + dataSnaSybase.equals(dataSnaPos)+" НЗА -");
            arestDAOSybase.findArestDataSna(dataSnaPos);
            //updateArest
        } else {
            System.out.println(dataSnaSybase + " else > " + dataSnaPos + " = " + dataSnaSybase.equals(dataSnaPos));
        }

        model.addAttribute("dataSnaPos", dataSnaPos);
        model.addAttribute("dataSnaSybase", dataSnaSybase);
        model.addAttribute("records", records);
        return "karta/test";
    }
}
