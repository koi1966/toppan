package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.repo.ArestRepository;
import toppan.example.toppan.models.repo.KartaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class Service {
    private final ArestRepository arestRepository;
    private final KartaRepository kartaRepository;
    private final ArestDAOPostgres arestDAOPostgres;

    public Service(KartaRepository kartaRepository, ArestDAOPostgres arestDAOPostgres,
                   ArestRepository arestRepository) {
        this.kartaRepository = kartaRepository;
        this.arestDAOPostgres = arestDAOPostgres;
        this.arestRepository = arestRepository;
    }

    public List<Karta> findKartaZnak(String znak) {
        return kartaRepository.findByZnak(znak);
    }

    public void arestSybase(ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {

            int record = arestRepository.updateArest(resultSet.getTimestamp("data_sna"), resultSet.getTimestamp("time_fix"),
                    resultSet.getString("Kart_id"), resultSet.getString("commenta"), resultSet.getTimestamp("data_out"),
                    resultSet.getTimestamp("k_data2"), resultSet.getString("k_nom2"), resultSet.getString("oper_out"),
                    resultSet.getString("out_nom"), resultSet.getString("who_sha"), resultSet.getString("ish_answer1"),
                    resultSet.getString("ish_answer2")
            );

        }

    }

}
