package toppan.example.toppan.mapper;

import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.dto.KartaDTO;

import java.util.List;


@org.mapstruct.Mapper
public interface Mapper {

    KartaDTO kartaToKartaDto(Karta karta);

    Karta kartaDtoToKarta(KartaDTO kartaDTO);

//    ArticleDTO articleToArticleDTO(Article article);
//
//    Article articleDtoToArticle(ArticleDTO articleDTO);
//
    List<KartaDTO> map(List<Karta> kartaList);
}
