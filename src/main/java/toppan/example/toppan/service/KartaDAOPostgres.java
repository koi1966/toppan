package toppan.example.toppan.service;

import toppan.example.toppan.models.ArestSybase;
import toppan.example.toppan.models.KartaSybase;
import toppan.example.toppan.models.OperSybase;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static toppan.example.toppan.bl.DataDAOPostgres.connectionPos;

@Component
public class KartaDAOPostgres {

    public void addKarta(KartaSybase kartaS) {

        try {
            PreparedStatement statementPostgres = connectionPos.prepareStatement("INSERT INTO karta ( kart_id, data_oper,reg_def," +
                    "num_dv,num_cuz,num_shas,data_v,color,cuzov,tip,marka,model,who,teh_pasp,type_zn,znak,annot," +
                    "code_oper,nom_naklad,data_naklad,masa,status,region,ministr_name,insp,ispekt_osm,who_out,regim," +
                    "power,volume,cylinder,door,fuel,place,tom,sob_id,family,fname,sec_name,family_lat,fname_lat,permis," +
                    "born,pasport,pasp_cto,obl,rajon,city,street,house,corp,kv,tel,tel_work,office,dolj,obl_g,rajon_g," +
                    "city_g,street_g,corp_g,house_g,tel_g,masa1) " +
                    "Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            statementPostgres.setLong(1, kartaS.getId());
            statementPostgres.setString(2, kartaS.getKart_id());
            statementPostgres.setTimestamp(3, kartaS.getData_oper());
            statementPostgres.setInt(4, kartaS.getReg_def());
            statementPostgres.setString(5, kartaS.getNum_dv());
            statementPostgres.setString(6, kartaS.getNum_cuz());
            statementPostgres.setString(7, kartaS.getNum_shas());
            statementPostgres.setInt(8, kartaS.getData_v());
            statementPostgres.setString(9, kartaS.getColor());
            statementPostgres.setString(10, kartaS.getCuzov());
            statementPostgres.setString(11, kartaS.getTip());
            statementPostgres.setString(12, kartaS.getMarka());
            statementPostgres.setString(13, kartaS.getModel());
            statementPostgres.setInt(14, kartaS.getWho());
            statementPostgres.setString(15, kartaS.getTeh_pasp());
            statementPostgres.setInt(16, kartaS.getType_zn());
            statementPostgres.setString(17, kartaS.getZnak());
            statementPostgres.setString(18, kartaS.getAnnot());
            statementPostgres.setString(19, kartaS.getCode_oper());
            statementPostgres.setInt(20, kartaS.getNom_naklad());
            statementPostgres.setTimestamp(21, (kartaS.getData_naklad()));
            statementPostgres.setInt(22, kartaS.getMasa());
            statementPostgres.setInt(23, kartaS.getStatus());
            statementPostgres.setString(24, kartaS.getRegion());
            statementPostgres.setString(25, kartaS.getMinistr_name());
            statementPostgres.setString(26, kartaS.getInsp());
            statementPostgres.setString(27, kartaS.getIspekt_osm());
            statementPostgres.setString(28, kartaS.getWho_out());
            statementPostgres.setInt(29, kartaS.getRegim());
            statementPostgres.setString(30, kartaS.getPower());
            statementPostgres.setString(31, kartaS.getVolume());
            statementPostgres.setString(32, kartaS.getCylinder());
            statementPostgres.setString(33, kartaS.getDoor());
            statementPostgres.setString(34, kartaS.getFuel());
            statementPostgres.setString(35, kartaS.getPlace());
            statementPostgres.setString(36, kartaS.getTom());
            statementPostgres.setString(37, kartaS.getSob_id());
            statementPostgres.setString(38, kartaS.getFamily());
            statementPostgres.setString(39, kartaS.getFname());
            statementPostgres.setString(40, kartaS.getSec_name());
            statementPostgres.setString(41, kartaS.getFamily_lat());
            statementPostgres.setString(42, kartaS.getFname_lat());
            statementPostgres.setString(43, kartaS.getPermis());
            statementPostgres.setTimestamp(44, kartaS.getBorn());
            statementPostgres.setString(45, kartaS.getPasport());
            statementPostgres.setString(46, kartaS.getPasp_cto());
            statementPostgres.setString(47, kartaS.getObl());
            statementPostgres.setString(48, kartaS.getRajon());
            statementPostgres.setString(49, kartaS.getCity());
            statementPostgres.setString(50, kartaS.getStreet());
            statementPostgres.setString(51, kartaS.getHouse());
            statementPostgres.setString(52, kartaS.getCorp());
            statementPostgres.setString(53, kartaS.getKv());
            statementPostgres.setString(54, kartaS.getTel());
            statementPostgres.setString(55, kartaS.getTel_work());
            statementPostgres.setString(56, kartaS.getOffice());
            statementPostgres.setString(57, kartaS.getDolj());
            statementPostgres.setString(58, kartaS.getObl_g());
            statementPostgres.setString(59, kartaS.getRajon_g());
            statementPostgres.setString(60, kartaS.getCity_g());
            statementPostgres.setString(61, kartaS.getStreet_g());
            statementPostgres.setString(62, kartaS.getCorp_g());
            statementPostgres.setString(63, kartaS.getHouse_g());
            statementPostgres.setString(64, kartaS.getTel_g());
            statementPostgres.setInt(65, kartaS.getMasa1());

            statementPostgres.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addArest(ArestSybase arestSybase) {

        PreparedStatement statementPostgres = null;

//        try {
//            statementPostgres = connectionPos.prepareStatement("INSERT INTO arest (data_arest," +
//                    "kart_id,who_na,in_nom,data_in,data_sna,who_sha,out_nom,data_out,oper_in,oper_out,vlad,ish_answer1," +
//                    "ish_answer2,k_nom1,k_data1,k_nom2,k_data2,commenta,time_fix) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
//                    "?,?,?,?,?)");

            try {
                statementPostgres = connectionPos.prepareStatement("INSERT INTO arest (data_arest," +
                        "kart_id,who_na,in_nom,data_in,data_sna,who_sha,out_nom,data_out,oper_in,oper_out,vlad,ish_answer1," +
                        "ish_answer2,k_nom1,k_data1,k_nom2,k_data2,commenta,time_fix) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?)");

            statementPostgres.setTimestamp(1, arestSybase.getData_arest());
            statementPostgres.setString(2, arestSybase.getKart_id());
            statementPostgres.setString(3, arestSybase.getWho_na());
            statementPostgres.setString(4, arestSybase.getIn_nom());
            try {
                statementPostgres.setTimestamp(5, arestSybase.getData_in());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                statementPostgres.setDate(5, Date.valueOf("1900-01-01"));
            }
            statementPostgres.setTimestamp(6, arestSybase.getData_sna());
            statementPostgres.setString(7, arestSybase.getWho_sha());
            statementPostgres.setString(8, arestSybase.getOut_nom());
            try {
                statementPostgres.setTimestamp(9, arestSybase.getData_out());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                statementPostgres.setDate(9, Date.valueOf("1900-01-01"));
            }
            statementPostgres.setString(10, arestSybase.getOper_in());
            statementPostgres.setString(11, arestSybase.getOper_out());
            statementPostgres.setString(12, arestSybase.getVlad());
            statementPostgres.setInt(13, arestSybase.getIsh_answer1());
            try {
                statementPostgres.setInt(14, arestSybase.getIsh_answer2());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                statementPostgres.setInt(14, 0);
            }
            statementPostgres.setString(15, arestSybase.getK_nom1());
            statementPostgres.setTimestamp(16, arestSybase.getK_data1());
            statementPostgres.setString(17, arestSybase.getK_nom2());
            statementPostgres.setTimestamp(18, arestSybase.getK_data2());
            statementPostgres.setString(19, arestSybase.getCommenta());
            statementPostgres.setTimestamp(20, arestSybase.getTime_fix());

            statementPostgres.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOper(OperSybase operSybase) {
        PreparedStatement statementPostgres = null;

        try {
            statementPostgres = connectionPos.prepareStatement("INSERT INTO oper (" +
                    "oper_id,sod_id,oper,ch_field,registration,vydacha,tip_registr,izjat,zapros,commenta,oper_nic,osn_para) " +
                    "Values (?,?,?,?,?,?,?,?,?,?,?,?)");

            statementPostgres.setString(1, operSybase.getOper_id());
            statementPostgres.setString(2, operSybase.getSod_id());
            statementPostgres.setString(3, operSybase.getOper());
            statementPostgres.setString(4, operSybase.getCh_field());
            statementPostgres.setInt(5, operSybase.getRegistration());
            statementPostgres.setInt(6, operSybase.getVydacha());
            statementPostgres.setInt(7, operSybase.getTip_registr());
            statementPostgres.setInt(8, operSybase.getIzjat());
            statementPostgres.setInt(9, operSybase.getZapros());
            statementPostgres.setString(10, operSybase.getCommenta());
            statementPostgres.setInt(11, operSybase.getOper_nic());
            statementPostgres.setString(12, operSybase.getOsn_para());

            statementPostgres.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
