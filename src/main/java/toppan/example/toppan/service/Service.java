package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import toppan.example.toppan.models.ArestSybase;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.repo.KartaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class Service {
    private final KartaRepository kartaRepository;
    private final ArestDAOPostgres arestDAOPostgres;

    public Service(KartaRepository kartaRepository, ArestDAOPostgres arestDAOPostgres) {
        this.kartaRepository = kartaRepository;
        this.arestDAOPostgres = arestDAOPostgres;
    }

    public List<Karta> findKartaZnak(String znak) {
        return kartaRepository.findByZnak(znak);
    }

    public ArestSybase arestSybase(ResultSet resultSet) throws SQLException {

        ArestSybase arestSybase1 = new ArestSybase();
        arestSybase1.setData_arest(resultSet.getTimestamp("data_arest"));
        arestSybase1.setKart_id(resultSet.getString("Kart_id"));
        arestSybase1.setWho_na(resultSet.getString("who_na"));
        arestSybase1.setIn_nom(resultSet.getString("in_nom"));
        arestSybase1.setData_in(resultSet.getTimestamp("data_in"));
        arestSybase1.setData_sna(resultSet.getTimestamp("data_sna"));
        arestSybase1.setWho_sha(resultSet.getString("who_sha"));
        arestSybase1.setOut_nom(resultSet.getString("out_nom"));
        arestSybase1.setData_out(resultSet.getTimestamp("data_out"));
        arestSybase1.setData_out(resultSet.getTimestamp("data_out"));
        arestSybase1.setOper_in(resultSet.getString("oper_in"));
        arestSybase1.setOper_out(resultSet.getString("oper_out"));
        arestSybase1.setVlad(resultSet.getString("vlad"));
        try {
            arestSybase1.setIsh_answer1(resultSet.getInt("ish_answer1"));
        } catch (NullPointerException e) {
//            System.out.println(e.getMessage());
            arestSybase1.setIsh_answer1(0);
        }
        try {
            arestSybase1.setIsh_answer1(resultSet.getInt("ish_answer2"));
        } catch (NullPointerException e) {
//            System.out.println(e.getMessage());
            arestSybase1.setIsh_answer2(0);
        }
        arestSybase1.setK_nom1(resultSet.getString("k_nom1"));
        arestSybase1.setK_data1(resultSet.getTimestamp("k_data1"));
        arestSybase1.setK_nom2(resultSet.getString("k_nom2"));
        arestSybase1.setK_data2(resultSet.getTimestamp("k_data2"));
        arestSybase1.setCommenta(resultSet.getString("commenta"));
        arestSybase1.setTime_fix(resultSet.getTimestamp("time_fix"));

        return arestSybase1;
    }
}
