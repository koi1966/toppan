package toppan.example.toppan.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import toppan.example.toppan.mapper.Mapper;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.dto.KartaDTO;
import toppan.example.toppan.service.Service;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KartControllerRest {

    private final Service service;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    @GetMapping("/znak")
    public List<KartaDTO> searchUser(@RequestParam String znak) {
        log.info("All users age > {}", znak);
        List<Karta> kartaList = service.findKartaZnak(znak);
        return mapper.mapKartaToKartaDto(kartaList);
    }

}
