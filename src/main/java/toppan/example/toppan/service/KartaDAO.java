package toppan.example.toppan.service;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import toppan.example.toppan.models.Karta;
import toppan.example.toppan.models.repo.KartaRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import static sun.security.util.ArrayUtil.reverse;
import static toppan.example.toppan.bl.DataDAOPostgres.connectionPos;

@Component
@Service
public class KartaDAO {
    private final KartaRepository kartaRepository;

    public KartaDAO(KartaRepository kartaRepository) {
        this.kartaRepository = kartaRepository;
    }

    public List<Karta> search(Karta kar, String check) {

        List<Karta> kart = new ArrayList<>();

        Statement statement = null;
        try {
            statement = connectionPos.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String SQLa = "SELECT karta.*, oper.oper from karta, oper where ";
        String SqlEquals = "SELECT karta.*, oper.oper from karta, oper where ";

        if (!kar.getZnak().isEmpty()) {

            SQLa = SQLa + "znak like '" + kar.getZnak().toUpperCase() + "' ";
        }


        if (!kar.getKart_id().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {

                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "kart_id like '" + kar.getKart_id().toUpperCase() + "' ";
        }

        if (!kar.getNum_cuz().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "num_cuz like reverse('" + kar.getNum_cuz().toUpperCase() + "') ";
        }

        if (!kar.getNum_shas().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }

            SQLa = SQLa + "num_shas like reverse('" + kar.getNum_shas().toUpperCase() + "') ";
        }

        if (!kar.getNum_dv().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "num_dv like reverse('" + kar.getNum_dv().toUpperCase() + "') ";
        }

        if (!kar.getFamily().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "family like '" + kar.getFamily().toUpperCase() + "' ";
        }

        if (!kar.getFname().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "fname like '" + kar.getFname().toUpperCase() + "' ";
        }

        if (!kar.getSec_name().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "sec_name like '" + kar.getSec_name().toUpperCase() + "' ";
        }

        if (!kar.getPasport().isEmpty()) {
            if (!StringEquals.equalsSQL(SQLa, SqlEquals)) {
                SQLa = SQLa + "and ";
            }
            SQLa = SQLa + "pasport like '" + kar.getPasport().toUpperCase() + "' ";
        }

        if (check.equals("on")) {
            SQLa = SQLa +
                    "and karta.data_oper = (select max(kk.data_oper) from karta kk where kk.kart_id = karta.kart_id ) ";
        }
        SQLa = SQLa + "and substring(karta.code_oper,1,2)=oper.oper_id ";
        SQLa = SQLa + "ORDER BY kart_id, Data_oper";
//        int rowcount = 0;
        try {
            assert statement != null;
            try (ResultSet resultSet = statement.executeQuery(SQLa)) {
                while (resultSet.next()) {
//                    rowcount++;
                    Karta AMT = new Karta();
                    AMT.setKart_id(resultSet.getString("Kart_id"));
                    AMT.setRegion(resultSet.getString("Region"));
                    AMT.setId(resultSet.getLong("id"));
                    AMT.setFamily(resultSet.getString("Family"));
                    AMT.setRajon(resultSet.getString("Rajon"));
                    AMT.setStreet(resultSet.getString("Street"));
                    AMT.setHouse(resultSet.getString("House"));
                    AMT.setKv(resultSet.getString("Kv"));
                    AMT.setFname(resultSet.getString("Fname"));
                    AMT.setData_oper(resultSet.getTimestamp("Data_oper"));
                    AMT.setSec_name(resultSet.getString("Sec_name"));
                    AMT.setColor(resultSet.getString("Color"));
                    AMT.setData_v(resultSet.getInt("Data_v"));
                    AMT.setMarka(resultSet.getString("Marka"));
                    AMT.setModel(resultSet.getString("Model"));
                    AMT.setZnak(resultSet.getString("Znak"));
                    AMT.setTeh_pasp(resultSet.getString("Teh_pasp"));
                    AMT.setNum_cuz(resultSet.getString("Num_cuz"));
                    AMT.setCode_oper(resultSet.getString("oper"));
                    AMT.setCuzov(resultSet.getString("cuzov"));

                    kart.add(AMT);
                }
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return kart;
    }

    public List<Karta> AmtHistory(long id) {
        List<Karta> kartHistory = new ArrayList<>();

        String SQL =
                "select karta.id,region,kart_id,data_oper,data_v,znak,kv,teh_pasp,family,fname,sec_name,born,pasport,permis,house,street," +
                        "teh_pasp,color,(marka ||' '|| model) as marka,reverse(karta.num_cuz) as num_cuz,reverse(karta.num_shas) as num_shas," +
                        "reverse(karta.num_dv) as num_dv,power,volume,door,fuel,tip,annot,cuzov,city,rajon,obl,znak, oper.* " +
                        "from karta, oper " +
                        "where kart_id in (Select k2.kart_id from karta k2 where k2.id=?) " +
                        "and substring(karta.code_oper,1,2)=oper.oper_id ORDER BY data_oper DESC";

        try {
            PreparedStatement preparedStatement = connectionPos.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Karta AMTh = new Karta();
                    AMTh.setId(resultSet.getLong("id"));
                    AMTh.setKart_id(resultSet.getString("Kart_id"));
                    AMTh.setRegion(resultSet.getString("Region"));
                    AMTh.setData_oper(resultSet.getTimestamp("Data_oper"));
                    AMTh.setZnak(resultSet.getString("Znak"));
                    AMTh.setTeh_pasp(resultSet.getString("Teh_pasp"));
                    AMTh.setNum_cuz(resultSet.getString("Num_cuz"));
                    AMTh.setNum_shas(resultSet.getString("Num_shas"));
                    AMTh.setNum_dv(resultSet.getString("Num_dv"));
                    AMTh.setData_v(resultSet.getInt("Data_v"));
                    AMTh.setColor(resultSet.getString("Color"));
                    AMTh.setMarka(resultSet.getString("Marka"));
                    AMTh.setFamily(resultSet.getString("Family"));
                    AMTh.setFname(resultSet.getString("Fname"));
                    AMTh.setSec_name(resultSet.getString("Sec_name"));
                    AMTh.setBorn(resultSet.getTimestamp("Born"));
                    AMTh.setSec_name(resultSet.getString("Sec_name"));
                    AMTh.setPasport(resultSet.getString("Pasport"));
                    AMTh.setPermis(resultSet.getString("Permis"));
                    AMTh.setAnnot(resultSet.getString("Annot"));
                    AMTh.setPower(resultSet.getString("Power"));
                    AMTh.setVolume(resultSet.getString("Volume"));
                    AMTh.setDoor(resultSet.getString("Door"));
                    AMTh.setFuel(resultSet.getString("Fuel"));
                    AMTh.setTip(resultSet.getString("Tip"));
                    AMTh.setCuzov(resultSet.getString("Cuzov"));
                    AMTh.setObl(resultSet.getString("obl"));
                    AMTh.setRajon(resultSet.getString("rajon"));
                    AMTh.setCity(resultSet.getString("city"));
                    AMTh.setStreet(resultSet.getString("street"));
                    AMTh.setHouse(resultSet.getString("house"));
                    AMTh.setKv(resultSet.getString("kv"));
                    AMTh.setCode_oper(resultSet.getString("oper"));
                    kartHistory.add(AMTh);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return kartHistory;
    }

    public List<Karta> kartaList(String znak) {

        return kartaRepository.findByZnak(znak);
    }
}
