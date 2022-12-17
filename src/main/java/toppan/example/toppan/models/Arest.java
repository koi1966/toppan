package toppan.example.toppan.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
public class Arest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Timestamp data_arest;
    //    not null
    private String kart_id;
    //    not null
    private String who_na;
    private String in_nom;
    private Date data_in;
    private Date data_sna ;
    private String who_sha;
    private String out_nom;
    private Date data_out;
    private String oper_in;
    private String oper_out;
    private String vlad ;
    private int ish_answer1;
    private int ish_answer2;
    private String k_nom1;
    private Timestamp k_data1;
    private String k_nom2 ;
    private Timestamp k_data2;
    private String commenta;
    //    not null
    private Timestamp time_fix;
    public Arest() {
    }

}
