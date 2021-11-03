package toppan.example.toppan.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="rubin", indexes = {@Index(name = "data_v", columnList = "data_v",unique = false)})
public class Rubin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // week -   за неділю           Кількість звернень громадян щодо видачі довідок про відсутність (наявність) судимості
    // week_1 - за неділю           Кількість виданих довідок про відсутність (наявність) судимості
    //
    // year -   З початку року   Кількість звернень громадян щодо видачі довідок про відсутність (наявність) судимості
    // year_1 - З початку року   Кількість виданих довідок про відсутність (наявність) судимості
    private String pidrozdil;
    private int week, week_1, year, year_1;
    private Date data_v;

    public Rubin() {
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek_1() {
        return week_1;
    }

    public void setWeek_1(int week_1) {
        this.week_1 = week_1;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear_1() {
        return year_1;
    }

    public void setYear_1(int year_1) {
        this.year_1 = year_1;
    }

    public Date getData_v() {
        return data_v;
    }

    public void setData_v(Date data_v) {
        this.data_v = data_v;
    }

    public Rubin(String pidrozdil, int week, int week_1, int year, int year_1, Date data_v) {
        this.pidrozdil = pidrozdil;
        this.week = week;
        this.week_1 = week_1;
        this.year = year;
        this.year_1 = year_1;
        this.data_v = data_v;
    }
}



