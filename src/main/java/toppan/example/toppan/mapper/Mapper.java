package toppan.example.toppan.mapper;

import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.dto.KartaDTO;

import java.util.List;


@org.mapstruct.Mapper
public interface Mapper {

    List<KartaDTO> map(List<Karta> kartaList);
}
