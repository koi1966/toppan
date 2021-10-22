package toppan.example.toppan.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class toppan {
    //    серийный номер принтера Toppan CP500
    @Id
    private String sn;
    //    Дата ввода в експлуатацию, комплектность, номенклатурный код
    private String datawork, completeness, code;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDatawork() {
        return datawork;
    }

    public void setDatawork(String datawork) {
        this.datawork = datawork;
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

    public toppan(String sn, String datawork, String completeness, String code) {
        this.sn = sn;
        this.datawork = datawork;
        this.completeness = completeness;
        this.code = code;
    }

    public toppan() {
    }
}




