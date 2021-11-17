package toppan.example.toppan.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    @NotNull(message = "Введіть значення")
    @NotEmpty(message = "Введіть значення")
    @Min(value = 0, message = "Значення мають бути більше за - 0")
    private int week, week_1, year_0, year_1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotEmpty( message = "Введіть дату")
    private LocalDate data_v;

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

    public int getYear_0() {
        return year_0;
    }

    public void setYear_0(int year_0) {
        this.year_0 = year_0;
    }

    public int getYear_1() {
        return year_1;
    }

    public void setYear_1(int year_1) {
        this.year_1 = year_1;
    }

    public LocalDate getData_v() {
        return data_v;
    }

    public void setData_v(@NotEmpty(message = "Введіть дату") LocalDate data_v) {
        this.data_v = data_v;
    }


}



