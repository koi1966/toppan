package toppan.example.toppan.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class KartaSybase {
    private Long id;
    private String kart_id;
    private Timestamp data_oper;
    private int reg_def;
    private String num_dv;
    private String num_cuz;
    private String num_shas;
    private int data_v;
    private String color;
    private String cuzov;
    private String tip;
    private String marka;
    private String model;
    private int who;
    private String teh_pasp;
    private int type_zn;
    private String znak;
    private String annot;
    private String code_oper;
    private int nom_naklad;
    private Timestamp data_naklad;
    private int masa;
    private int status;
    private String region;
    private String ministr_name;
    private String insp;
    private String ispekt_osm;
    private String who_out;
    private int regim;
    private String power;
    private String volume;
    private String cylinder;
    private String door;
    private String fuel;
    private String place;
    private String tom;
    private String sob_id;
    private String family;
    private String fname;
    private String sec_name;
    private String family_lat;
    private String fname_lat;
    private String permis;
    private Timestamp born;
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
    private String obl_g;
    private String rajon_g;
    private String city_g;
    private String street_g;
    private String corp_g;
    private String house_g;
    private String tel_g;
    private    int masa1;

    public KartaSybase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKart_id() {
        return kart_id;
    }

    public void setKart_id(String kart_id) {
        this.kart_id = kart_id;
    }

    public Timestamp getData_oper() {
        return data_oper;
    }

    public void setData_oper(Timestamp data_oper) {
        this.data_oper = data_oper;
    }

    public int getReg_def() {
        return reg_def;
    }

    public void setReg_def(int reg_def) {
        this.reg_def = reg_def;
    }

    public String getNum_dv() {
        return num_dv;
    }

    public void setNum_dv(String num_dv) {
        this.num_dv = num_dv;
    }

    public String getNum_cuz() {
        return num_cuz;
    }

    public void setNum_cuz(String num_cuz) {
        this.num_cuz = num_cuz;
    }

    public String getNum_shas() {
        return num_shas;
    }

    public void setNum_shas(String num_shas) {
        this.num_shas = num_shas;
    }

    public int getData_v() {
        return data_v;
    }

    public void setData_v(int data_v) {
        this.data_v = data_v;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCuzov() {
        return cuzov;
    }

    public void setCuzov(String cuzov) {
        this.cuzov = cuzov;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public String getTeh_pasp() {
        return teh_pasp;
    }

    public void setTeh_pasp(String teh_pasp) {
        this.teh_pasp = teh_pasp;
    }

    public int getType_zn() {
        return type_zn;
    }

    public void setType_zn(int type_zn) {
        this.type_zn = type_zn;
    }

    public String getZnak() {
        return znak;
    }

    public void setZnak(String znak) {
        this.znak = znak;
    }

    public String getAnnot() {
        return annot;
    }

    public void setAnnot(String annot) {
        this.annot = annot;
    }

    public String getCode_oper() {
        return code_oper;
    }

    public void setCode_oper(String code_oper) {
        this.code_oper = code_oper;
    }

    public int getNom_naklad() {
        return nom_naklad;
    }

    public void setNom_naklad(int nom_naklad) {
        this.nom_naklad = nom_naklad;
    }

    public Timestamp getData_naklad() {
        return data_naklad;
    }

    public void setData_naklad(Timestamp data_naklad) {
        this.data_naklad = data_naklad;
    }

    public int getMasa() {
        return masa;
    }

    public void setMasa(int masa) {
        this.masa = masa;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMinistr_name() {
        return ministr_name;
    }

    public void setMinistr_name(String ministr_name) {
        this.ministr_name = ministr_name;
    }

    public String getInsp() {
        return insp;
    }

    public void setInsp(String insp) {
        this.insp = insp;
    }

    public String getIspekt_osm() {
        return ispekt_osm;
    }

    public void setIspekt_osm(String ispekt_osm) {
        this.ispekt_osm = ispekt_osm;
    }

    public String getWho_out() {
        return who_out;
    }

    public void setWho_out(String who_out) {
        this.who_out = who_out;
    }

    public int getRegim() {
        return regim;
    }

    public void setRegim(int regim) {
        this.regim = regim;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCylinder() {
        return cylinder;
    }

    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTom() {
        return tom;
    }

    public void setTom(String tom) {
        this.tom = tom;
    }

    public String getSob_id() {
        return sob_id;
    }

    public void setSob_id(String sob_id) {
        this.sob_id = sob_id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getFamily_lat() {
        return family_lat;
    }

    public void setFamily_lat(String family_lat) {
        this.family_lat = family_lat;
    }

    public String getFname_lat() {
        return fname_lat;
    }

    public void setFname_lat(String fname_lat) {
        this.fname_lat = fname_lat;
    }

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public Timestamp getBorn() {
        return born;
    }

    public void setBorn(Timestamp born) {
        this.born = born;
    }

    public String getPasport() {
        return pasport;
    }

    public void setPasport(String pasport) {
        this.pasport = pasport;
    }

    public String getPasp_cto() {
        return pasp_cto;
    }

    public void setPasp_cto(String pasp_cto) {
        this.pasp_cto = pasp_cto;
    }

    public String getObl() {
        return obl;
    }

    public void setObl(String obl) {
        this.obl = obl;
    }

    public String getRajon() {
        return rajon;
    }

    public void setRajon(String rajon) {
        this.rajon = rajon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getKv() {
        return kv;
    }

    public void setKv(String kv) {
        this.kv = kv;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel_work() {
        return tel_work;
    }

    public void setTel_work(String tel_work) {
        this.tel_work = tel_work;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getDolj() {
        return dolj;
    }

    public void setDolj(String dolj) {
        this.dolj = dolj;
    }

    public String getObl_g() {
        return obl_g;
    }

    public void setObl_g(String obl_g) {
        this.obl_g = obl_g;
    }

    public String getRajon_g() {
        return rajon_g;
    }

    public void setRajon_g(String rajon_g) {
        this.rajon_g = rajon_g;
    }

    public String getCity_g() {
        return city_g;
    }

    public void setCity_g(String city_g) {
        this.city_g = city_g;
    }

    public String getStreet_g() {
        return street_g;
    }

    public void setStreet_g(String street_g) {
        this.street_g = street_g;
    }

    public String getCorp_g() {
        return corp_g;
    }

    public void setCorp_g(String corp_g) {
        this.corp_g = corp_g;
    }

    public String getHouse_g() {
        return house_g;
    }

    public void setHouse_g(String house_g) {
        this.house_g = house_g;
    }

    public String getTel_g() {
        return tel_g;
    }

    public void setTel_g(String tel_g) {
        this.tel_g = tel_g;
    }

    public int getMasa1() {
        return masa1;
    }

    public void setMasa1(int masa1) {
        this.masa1 = masa1;
    }
}
