package toppan.example.toppan.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Rubin_year {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pidrozdil;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate data_v; // звіт на останній день місяця
    //    year_appeal  - обращений з початку року
    //    year_issued  - выдано з початку року
    private int year_appeal, year_issued;

    public Rubin_year() {
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

    public LocalDate getData_v() {
        return data_v;
    }

    public void setData_v(LocalDate data_v) {
        this.data_v = data_v;
    }

    public int getYear_appeal() {
        return year_appeal;
    }

    public void setYear_appeal(int year_appeal) {
        this.year_appeal = year_appeal;
    }

    public int getYear_issued() {
        return year_issued;
    }

    public void setYear_issued(int year_issued) {
        this.year_issued = year_issued;
    }

}
