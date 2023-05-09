package toppan.example.toppan.service;

//select karta.*
//        from karta
//        where not exists (
//        select 1
//        from karta2
//        where karta2.id = karta.id);

import toppan.example.toppan.bl.DataDAOSybase;
import toppan.example.toppan.models.ArestSybase;
import toppan.example.toppan.models.KartaSybase;
import toppan.example.toppan.models.OperSybase;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import static toppan.example.toppan.bl.DataDAOSybase.connectionSa;


@Component
public class KartaDAOSybase {
    private final DataDAOSybase dataDAOSybase;
    private final KartaDAOPostgres kartaDAOPostgres;

    public KartaDAOSybase(DataDAOSybase dataDAOSybase, KartaDAOPostgres kartaDAOPostgres) {
        this.dataDAOSybase = dataDAOSybase;
        this.kartaDAOPostgres = kartaDAOPostgres;
    }

    public List<KartaSybase> searchKarta() throws SQLException {
        final Connection connectionSa = dataDAOSybase.getConnectionSa();
        List<KartaSybase> kartaSybaseAMTList = new ArrayList<>();

        PreparedStatement preparedStatement = connectionSa.prepareStatement("SELECT * from karta WHERE id BETWEEN ? AND ?");
        long minId = 31550000000308965L;
        long maxId = 31750000000308968L;

        try {
//            Warning:(42, 50) Method invocation 'executeQuery' may produce 'NullPointerException'

            {

                System.out.println("minId - " + minId + "  maxId - " + maxId);
                for (; maxId < 31750000000508968L; ) {
                    preparedStatement.setLong(1, minId);
                    preparedStatement.setLong(2, maxId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            KartaSybase kartaSybaseAMT = new KartaSybase();
                            kartaSybaseAMT.setId(resultSet.getLong("id"));
                            kartaSybaseAMT.setKart_id(resultSet.getString("Kart_id"));
                            kartaSybaseAMT.setData_oper(resultSet.getTimestamp("Data_oper"));
                            kartaSybaseAMT.setReg_def(resultSet.getInt("reg_def"));
                            kartaSybaseAMT.setNum_dv(resultSet.getString("num_dv"));
                            kartaSybaseAMT.setNum_cuz(resultSet.getString("num_cuz"));
                            kartaSybaseAMT.setNum_shas(resultSet.getString("num_shas"));
                            kartaSybaseAMT.setData_v(resultSet.getInt("data_v"));
                            kartaSybaseAMT.setColor(resultSet.getString("color"));
                            kartaSybaseAMT.setCuzov(resultSet.getString("cuzov"));
                            kartaSybaseAMT.setTip(resultSet.getString("tip"));
                            kartaSybaseAMT.setMarka(resultSet.getString("marka"));
                            kartaSybaseAMT.setModel(resultSet.getString("model"));
                            kartaSybaseAMT.setWho(resultSet.getInt("who"));
                            kartaSybaseAMT.setTeh_pasp(resultSet.getString("teh_pasp"));
                            kartaSybaseAMT.setType_zn(resultSet.getInt("type_zn"));
                            kartaSybaseAMT.setZnak(resultSet.getString("znak"));
                            kartaSybaseAMT.setAnnot(resultSet.getString("annot"));
                            kartaSybaseAMT.setCode_oper(resultSet.getString("code_oper"));
                            kartaSybaseAMT.setNom_naklad(resultSet.getInt("nom_naklad"));
                            try {
                                kartaSybaseAMT.setData_naklad(resultSet.getTimestamp("data_naklad"));
                            } catch (NullPointerException e) {
//                        System.out.println(e.getMessage());
                                kartaSybaseAMT.setData_naklad(Timestamp.valueOf("1900-01-01"));
                            }
                            kartaSybaseAMT.setMasa(resultSet.getInt("masa"));
                            kartaSybaseAMT.setStatus(resultSet.getInt("status"));
                            kartaSybaseAMT.setRegion(resultSet.getString("region"));
                            kartaSybaseAMT.setMinistr_name(resultSet.getString("ministr_name"));
                            kartaSybaseAMT.setInsp(resultSet.getString("insp"));
                            kartaSybaseAMT.setIspekt_osm(resultSet.getString("ispekt_osm"));
                            kartaSybaseAMT.setWho_out(resultSet.getString("who_out"));
                            kartaSybaseAMT.setRegim(resultSet.getInt("regim"));
                            kartaSybaseAMT.setPower(resultSet.getString("power"));
                            kartaSybaseAMT.setVolume(resultSet.getString("volume"));
                            kartaSybaseAMT.setCylinder(resultSet.getString("cylinder"));
                            kartaSybaseAMT.setDoor(resultSet.getString("door"));
                            kartaSybaseAMT.setFuel(resultSet.getString("fuel"));
                            kartaSybaseAMT.setPlace(resultSet.getString("place"));
                            kartaSybaseAMT.setTom(resultSet.getString("tom"));
                            kartaSybaseAMT.setSob_id(resultSet.getString("sob_id"));
                            kartaSybaseAMT.setFamily(resultSet.getString("family"));
                            kartaSybaseAMT.setFname(resultSet.getString("fname"));
                            kartaSybaseAMT.setSec_name(resultSet.getString("sec_name"));
                            kartaSybaseAMT.setFamily_lat(resultSet.getString("family_lat"));
                            kartaSybaseAMT.setFname_lat(resultSet.getString("fname_lat"));
                            kartaSybaseAMT.setPermis(resultSet.getString("permis"));
                            try {
                                kartaSybaseAMT.setBorn(resultSet.getTimestamp("born"));
                            } catch (NullPointerException e) {
//                        System.out.println(e.getMessage());
                                kartaSybaseAMT.setBorn(Timestamp.valueOf("1900-01-01"));
                            }
                            kartaSybaseAMT.setPasport(resultSet.getString("pasport"));
                            kartaSybaseAMT.setPasp_cto(resultSet.getString("pasp_cto"));
                            kartaSybaseAMT.setObl(resultSet.getString("obl"));
                            kartaSybaseAMT.setRajon(resultSet.getString("rajon"));
                            kartaSybaseAMT.setCity(resultSet.getString("city"));
                            kartaSybaseAMT.setStreet(resultSet.getString("street"));
                            kartaSybaseAMT.setHouse(resultSet.getString("house"));
                            kartaSybaseAMT.setCorp(resultSet.getString("corp"));
                            kartaSybaseAMT.setKv(resultSet.getString("kv"));
                            kartaSybaseAMT.setTel(resultSet.getString("tel"));
                            kartaSybaseAMT.setTel_work(resultSet.getString("tel_work"));
                            kartaSybaseAMT.setOffice(resultSet.getString("office"));
                            kartaSybaseAMT.setDolj(resultSet.getString("dolj"));
                            kartaSybaseAMT.setObl_g(resultSet.getString("obl_g"));
                            kartaSybaseAMT.setRajon_g(resultSet.getString("rajon_g"));
                            kartaSybaseAMT.setCity_g(resultSet.getString("city_g"));
                            kartaSybaseAMT.setStreet_g(resultSet.getString("street_g"));
                            kartaSybaseAMT.setCorp_g(resultSet.getString("corp_g"));
                            kartaSybaseAMT.setHouse_g(resultSet.getString("house_g"));
                            kartaSybaseAMT.setTel_g(resultSet.getString("tel_g"));
                            kartaSybaseAMT.setMasa1(resultSet.getInt("masa1"));

                            kartaDAOPostgres.addKarta(kartaSybaseAMT);
                        }
                    }
                    minId = maxId + 1;
                    maxId = maxId + 1000000000000000L;

                    System.out.println("minId - " + minId + "  maxId - " + maxId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connectionSa.close();
        return kartaSybaseAMTList;
    }

//    public List<OperSybase> searchOper() throws SQLException {
//        final Connection connectionSa = dataDAOSybase.getConnectionSa();
//        List<ArestSybase> arestSybaseList = new ArrayList<>();
//
//        PreparedStatement preparedStatement = connectionSa.prepareStatement("SELECT * from oper");
//
//        try (ResultSet resultSet = preparedStatement.executeQuery()) {
//            while (resultSet.next()) {
//                OperSybase operSybase = new OperSybase();
//                operSybase.setOper_id(resultSet.getString("oper_id"));
//                operSybase.setSod_id(resultSet.getString("sod_id"));
//                operSybase.setOper(resultSet.getString("oper"));
//                operSybase.setCh_field(resultSet.getString("ch_field"));
//                operSybase.setRegistration(resultSet.getInt("registration"));
//                operSybase.setVydacha(resultSet.getInt("vydacha"));
//                operSybase.setTip_registr(resultSet.getInt("tip_registr"));
//                operSybase.setIzjat(resultSet.getInt("izjat"));
//                operSybase.setZapros(resultSet.getInt("zapros"));
//                operSybase.setCommenta(resultSet.getString("commenta"));
//                operSybase.setOper_nic(resultSet.getInt("oper_nic"));
//                operSybase.setOsn_para(resultSet.getString("osn_para"));
//
//                kartaDAOPostgres.addOper(operSybase);
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }

    public Timestamp maxData_snaArestSybase() throws SQLException {
        final Connection connectionSa = dataDAOSybase.getConnectionSa();
        Timestamp dataSnaSybase = null;

        PreparedStatement preparedStatement = connectionSa.prepareStatement("select max(data_sna) as data_sna from dbo.arest where data_sna is not null");

        try (ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                dataSnaSybase = resultSet.getTimestamp("data_sna");
            }
        }
        connectionSa.close();
        return dataSnaSybase;
    }

}
