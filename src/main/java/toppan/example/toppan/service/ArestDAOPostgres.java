package toppan.example.toppan.service;

import org.springframework.stereotype.Component;
import toppan.example.toppan.models.Arest;
import toppan.example.toppan.models.ArestSybase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static toppan.example.toppan.bl.DataDAOPostgres.connectionPos;

/**
 *
 */
@Component
public class ArestDAOPostgres {

    public List<Arest> SearchArest(String kart_id) {
        List<Arest> ArAMT = new ArrayList<>();
        String SQL = "select * from arest where kart_id =? ORDER BY data_arest";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connectionPos.prepareStatement(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            assert preparedStatement != null;
            preparedStatement.setString(1, kart_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Arest ArestAMT = new Arest();
                ArestAMT.setData_arest(resultSet.getTimestamp("data_arest"));
                ArestAMT.setKart_id(resultSet.getString("kart_id"));
                ArestAMT.setWho_na(resultSet.getString("who_na"));
                ArestAMT.setIn_nom(resultSet.getString("in_nom"));
                ArestAMT.setData_in(resultSet.getTimestamp("data_in"));
                ArestAMT.setData_sna(resultSet.getTimestamp("data_sna"));
                ArestAMT.setWho_sha(resultSet.getString("who_sha"));
                ArestAMT.setOut_nom(resultSet.getString("out_nom"));
                ArestAMT.setData_out(resultSet.getTimestamp("data_out"));
                ArestAMT.setOper_in(resultSet.getString("oper_in"));
                ArestAMT.setOper_out(resultSet.getString("oper_out"));
                ArestAMT.setVlad(resultSet.getString("vlad"));
                ArestAMT.setIsh_answer1(resultSet.getInt("ish_answer1"));
                ArestAMT.setIsh_answer2(resultSet.getInt("ish_answer2"));
                ArestAMT.setK_nom1(resultSet.getString("k_nom1"));
                ArestAMT.setK_data1(resultSet.getTimestamp("k_data1"));
                ArestAMT.setK_nom2(resultSet.getString("k_nom2"));
                ArestAMT.setK_data2(resultSet.getTimestamp("k_data2")); // DateTime
                ArestAMT.setCommenta(resultSet.getString("commenta"));
                ArestAMT.setTime_fix(resultSet.getTimestamp("time_fix")); // DateTime

                ArAMT.add(ArestAMT);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ArAMT;
    }

    public List<Arest> updateArest(ArestSybase arestSybase) {

        return null ;
    }
}
