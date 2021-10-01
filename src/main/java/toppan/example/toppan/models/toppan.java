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
    private String work, completeness,code;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
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

    public toppan(String sn, String work, String completeness, String code) {
        this.sn = sn;
        this.work = work;
        this.completeness = completeness;
        this.code = code;
    }
    public toppan() {
    }
}




