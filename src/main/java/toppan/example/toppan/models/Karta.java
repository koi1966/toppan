package toppan.example.toppan.models;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j   // Логер
@Entity
//@Table(name = "Karta", indexes = {
//        @Index(name = "idx_karta_data_oper", columnList = "data_oper")
//})
public class Karta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String kart_id;
    private LocalDate data_oper;
    private int reg_def;
    private String num_dv;
    private String num_cuz ;
    private String num_shas ;
    private int data_v ;
    private String color ;
    private String cuzov ;
    private String tip ;
    private String marka ;
    private String model;
    private int who;
    private String teh_pasp ;
    private int type_zn ;
    private String znak ;
    private String annot ;
    private String code_oper ;
    private int nom_naklad ;
    private LocalDate data_naklad;
    private int masa;
    private int status;
    private String region ;
    private String ministr_name ;
    private String insp ;
    private String ispekt_osm ;
    private String who_out ;
    private int regim;
    private String power ;
    private String volume ;
    private String cylinder;
    private String door ;
    private String fuel;
    private String place;
    private String tom ;
    private String sob_id ;
    private String family;
    private String fname;
    private String sec_name;
    private String family_lat;
    private String fname_lat;
    private String permis;
    private LocalDate born ;
    private String pasport;
    private String pasp_cto;
    private String obl;
    private String rajon;
    private String city;
    private String street;
    private String house;
    private String corp;
    private String kv;
    private String tel;
    private String tel_work;
    private String office;
    private String dolj;
    private String obl_g ;
    private String rajon_g;
    private String city_g ;
    private String street_g;
    private String corp_g ;
    private String house_g;
    private String tel_g ;
    private int masa1 ;

    public Karta () {
    }
}
