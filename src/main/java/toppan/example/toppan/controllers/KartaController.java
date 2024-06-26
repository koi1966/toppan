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
import toppan.example.toppan.models.repo.ArestRepository;
import toppan.example.toppan.service.ArestDAOPostgres;
import toppan.example.toppan.service.ArestDAOSybase;
import toppan.example.toppan.service.KartaDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/karta")
public class KartaController {
    private final KartaDAO kartaDAO;
    private final ArestDAOSybase arestDAOSybase;
    private final ArestRepository arestRepository;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public KartaController(KartaDAO kartaDAO, ArestDAOSybase arestDAOSybase, ArestRepository arestRepository) {
        this.kartaDAO = kartaDAO;
        this.arestDAOSybase = arestDAOSybase;
        this.arestRepository = arestRepository;
    }

    @GetMapping("/searchAMT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String searchAMT(@ModelAttribute("karta") Karta karta) {
//        model.addAttribute("standardDate", new Date());
        return "karta/searchAMT";
    }

    // Получить с html формы поля для обработки @PostMapping
    @PostMapping()
    public String search(@ModelAttribute("karta") Karta kar,
                         @ModelAttribute("lastOper") String check,
                         @RequestParam(value = "data_bor",defaultValue = "1901-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data_born,
                         Model model) {

        final List<Karta> kartaAMTList = kartaDAO.search(kar, check,data_born);
        model.addAttribute("kartaSize", kartaAMTList.size());
        model.addAttribute("kartaList", kartaAMTList);
        LocalDate today = LocalDate.now();
        LocalDate dateEnd = LocalDate.of(2024, 11, 10);

        if (today.isAfter(dateEnd) )
            return "karta/search";
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

        return "karta/arestupdate";
    }

    @GetMapping("/searchKarta")
    public String testKarta2() {
        log.info("See is test !!!");

        return "karta/searchKarta";
    }

    @GetMapping(value = "/arestupdate")
    public String arestupdate(Model model) throws SQLException {

        long recordPos = arestRepository.countDataSnaIsNotNull();
        long recordSyb = arestDAOSybase.countArestDataSna();
        long records = recordSyb - recordPos;
        Timestamp dataSnaPos = arestRepository.maxDataSnaArestPostgres();
        Timestamp dataSnaSybase = arestDAOSybase.maxData_snaArestSybase();

        if (!dataSnaSybase.equals(dataSnaPos)) {
            // call a method
            System.out.println(dataSnaSybase + "  > " + dataSnaPos + " = " + dataSnaSybase.equals(dataSnaPos) + " НЗА -");
            arestDAOSybase.findArestDataSna(dataSnaPos);
            //updateArest
        } else {
            System.out.println(dataSnaSybase + " else > " + dataSnaPos + " = " + dataSnaSybase.equals(dataSnaPos));
        }

        model.addAttribute("dataSnaPos", dataSnaPos);
        model.addAttribute("dataSnaSybase", dataSnaSybase);
        model.addAttribute("records", records);
        return "karta/arestupdate";
    }

}
