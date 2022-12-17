package toppan.example.toppan.service;

import gai.data.springcourse.models.ArestSybase;
import org.springframework.stereotype.Component;
import toppan.example.toppan.models.Arest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static gai.data.springcourse.bl.DataDAO.connectionSa;

@Component
public class ArestDAO {

  public List<Arest> Serch_Arest(String kart_id) {
    List<Arest> ArAMT = new ArrayList<>();
    String SQL = "select * from arest where kart_id =? ORDER BY data_arest";

    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connectionSa.prepareStatement(SQL);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    try {
      preparedStatement.setString(1, kart_id);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    try {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        ArestSybase ArstAMT = new ArestSybase();
        ArstAMT.setData_arest(resultSet.getDate("data_arest"));
        ArstAMT.setKart_id(resultSet.getString("kart_id"));
        ArstAMT.setWho_na(resultSet.getString("who_na"));
        ArstAMT.setIn_nom(resultSet.getString("in_nom"));
        ArstAMT.setData_in(resultSet.getDate("data_in"));
        ArstAMT.setData_sna(resultSet.getDate("data_sna"));
        ArstAMT.setWho_sha(resultSet.getString("who_sha"));
        ArstAMT.setOut_nom(resultSet.getString("out_nom"));
        ArstAMT.setData_out(Date.valueOf(resultSet.getString("data_out")));
        ArstAMT.setOper_in(resultSet.getString("oper_in"));
        ArstAMT.setOper_out(resultSet.getString("oper_out"));
        ArstAMT.setVlad(resultSet.getString("vlad"));
        ArstAMT.setIsh_answer1(resultSet.getInt("ish_answer1"));
        ArstAMT.setIsh_answer2(resultSet.getInt("ish_answer2"));
        ArstAMT.setK_nom1(resultSet.getString("k_nom1"));
        ArstAMT.setK_data1(resultSet.getDate("k_data1"));
        ArstAMT.setK_nom2(resultSet.getString("k_nom2"));
        ArstAMT.setK_data2(resultSet.getDate("k_data2")); // DateTime
        ArstAMT.setCommenta(resultSet.getString("commenta"));
        ArstAMT.setTime_fix(resultSet.getTimestamp("time_fix")); // DateTime

        ArAMT.add(ArstAMT);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return ArAMT;
  }
}
