package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.repo.KartaRepository;

import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class Service {
    private final KartaRepository kartaRepository;

    public Service(KartaRepository kartaRepository) {
        this.kartaRepository = kartaRepository;
    }

    public List<Karta> findKartaZnak(String znak) {
        return kartaRepository.findByZnak(znak);
    }

}
