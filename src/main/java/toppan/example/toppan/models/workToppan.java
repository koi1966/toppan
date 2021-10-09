package toppan.example.toppan.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class workToppan {

    @Id
    private Long id;
    private Date date;
    private String title, anons, full_text,ip;
    private int views;

    public workToppan() {
    }
}
