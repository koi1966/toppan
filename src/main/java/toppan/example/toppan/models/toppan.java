package toppan.example.toppan.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class toppan {
//    серийный номер принтера Toppan CP500
    @Id
    private String sn;
//    Дата ввода в експлуатацию, комплектность, номенклатурный код
    private String gata_work, completeness, code;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getgata_work() {
        return gata_work;
    }

    public void setgata_work(String gata_work) {
        this.gata_work = gata_work;
    }

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public toppan(String sn, String gata_work, String completeness, String code) {
        this.sn = sn;
        this.gata_work = gata_work;
        this.completeness = completeness;
        this.code = code;
    }
    public toppan() {
    }
}




