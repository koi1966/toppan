package toppan.example.toppan.models;

import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.*;
import java.util.Date;

@Slf4j   // Логер
@Entity
public class Rubin_month {  //  месяц
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pidrozdil;
    private Date data_v;
    private String month_year; // месяц-год
    //    month_appeal  - обращений
    //    month_issued  - выдано
    private int month_appeal, month_issued;

    public Rubin_month() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPidrozdil() {
        return pidrozdil;
    }

    public void setPidrozdil(String pidrozdil) {
        this.pidrozdil = pidrozdil;
    }

    public Date getData_v() {
        return data_v;
    }

    public void setData_v(Date data_v) {
        this.data_v = data_v;
    }

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public int getMonth_appeal() {
        return month_appeal;
    }

    public void setMonth_appeal(int month_appeal) {
        this.month_appeal = month_appeal;
    }

    public int getMonth_issued() {
        return month_issued;
    }

    public void setMonth_issued(int month_issued) {
        this.month_issued = month_issued;
    }

    public Rubin_month(String pidrozdil, Date data_v, String month_year, int month_appeal, int month_issued) {
        this.pidrozdil = pidrozdil;
        this.data_v = data_v;
        this.month_year = month_year;
        this.month_appeal = month_appeal;
        this.month_issued = month_issued;
    }
}
