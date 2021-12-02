package toppan.example.toppan.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Rubin_week {   // неделя
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pidrozdil;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate data_v;
//    week_appeal  - обращений
//    week_issued  - выдано
    private int week_appeal, week_issued;

    public Rubin_week() {}

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

    public int getWeek_appeal() {
        return week_appeal;
    }

    public void setWeek_appeal(int week_appeal) {
        this.week_appeal = week_appeal;
    }

    public int getWeek_issued() {
        return week_issued;
    }

    public void setWeek_issued(int week_issued) {
        this.week_issued = week_issued;
    }

}

