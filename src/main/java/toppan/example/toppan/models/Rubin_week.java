package toppan.example.toppan.models;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j   // Логер
@Entity
@Table(name = "Rubin_week", indexes = {
        @Index(name = "idx_rubin_week_data_v", columnList = "data_v")
})
public class Rubin_week {   // неделя
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pidrozdil")
    private Pidrozdil pidrozdil;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_v")
    public LocalDate data;

    //          week_appeal  - обращений
    //                       week_issued  - выдано
    private int week_appeal, week_issued;

    public Rubin_week() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPidrozdil() {
        return pidrozdil.getPidrozdil();
    }

    public void setPidrozdil(Pidrozdil pidrozdil) {
        this.pidrozdil = pidrozdil;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data_v) {
        this.data = data_v;
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

