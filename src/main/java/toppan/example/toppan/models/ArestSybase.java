package toppan.example.toppan.models;

import java.sql.Timestamp;

public class ArestSybase {

  private Timestamp data_arest;
  private String kart_id;
  private String who_na;
  private String in_nom;
  private Timestamp data_in;
  private Timestamp data_sna;
  private String who_sha;
  private String out_nom;
  private Timestamp data_out;
  private String oper_in;
  private String oper_out;
  private String vlad;
  private Integer ish_answer1;
  private Integer ish_answer2;
  private String k_nom1;
  private Timestamp k_data1;
  private String k_nom2;
  private Timestamp k_data2;
  private String commenta;
  private Timestamp time_fix;

  public String getKart_id() {
    return kart_id;
  }

  public void setKart_id(String kart_id) {
    this.kart_id = kart_id;
  }

  public String getWho_na() {
    return who_na;
  }

  public void setWho_na(String who_na) {
    this.who_na = who_na;
  }

  public String getIn_nom() {
    return in_nom;
  }

  public void setIn_nom(String in_nom) {
    this.in_nom = in_nom;
  }

  public String getWho_sha() {
    return who_sha;
  }

  public void setWho_sha(String who_sha) {
    this.who_sha = who_sha;
  }

  public String getOut_nom() {
    return out_nom;
  }

  public void setOut_nom(String out_nom) {
    this.out_nom = out_nom;
  }

  public String getOper_in() {
    return oper_in;
  }

  public void setOper_in(String oper_in) {
    this.oper_in = oper_in;
  }

  public String getOper_out() {
    return oper_out;
  }

  public void setOper_out(String oper_out) {
    this.oper_out = oper_out;
  }

  public String getVlad() {
    return vlad;
  }

  public void setVlad(String vlad) {
    this.vlad = vlad;
  }

  public Integer getIsh_answer1() {
    return ish_answer1;
  }

  public void setIsh_answer1(Integer ish_answer1) {
    this.ish_answer1 = ish_answer1;
  }

  public Integer getIsh_answer2() {
    return ish_answer2;
  }

  public void setIsh_answer2(Integer ish_answer2) {
    this.ish_answer2 = ish_answer2;
  }

  public String getK_nom1() {
    return k_nom1;
  }

  public void setK_nom1(String k_nom1) {
    this.k_nom1 = k_nom1;
  }

  public String getK_nom2() {
    return k_nom2;
  }

  public void setK_nom2(String k_nom2) {
    this.k_nom2 = k_nom2;
  }

  public Timestamp getData_arest() {
    return data_arest;
  }

  public void setData_arest(Timestamp data_arest) {
    this.data_arest = data_arest;
  }

  public Timestamp getData_in() {
    return data_in;
  }

  public void setData_in(Timestamp data_in) {
    this.data_in = data_in;
  }

  public Timestamp getData_sna() {
    return data_sna;
  }

  public void setData_sna(Timestamp data_sna) {
    this.data_sna = data_sna;
  }

  public Timestamp getData_out() {
    return data_out;
  }

  public void setData_out(Timestamp data_out) {
    this.data_out = data_out;
  }

  public Timestamp getK_data1() {
    return k_data1;
  }

  public void setK_data1(Timestamp k_data1) {
    this.k_data1 = k_data1;
  }

  public Timestamp getK_data2() {
    return k_data2;
  }

  public void setK_data2(Timestamp k_data2) {
    this.k_data2 = k_data2;
  }

  public String getCommenta() {
    return commenta;
  }

  public void setCommenta(String commenta) {
    this.commenta = commenta;
  }

  public Timestamp getTime_fix() {
    return time_fix;
  }

  public void setTime_fix(Timestamp time_fix) {
    this.time_fix = time_fix;
  }

  public ArestSybase() {}
}
