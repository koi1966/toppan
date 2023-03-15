package toppan.example.toppan.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OperSybase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String oper_id;//not null
    //   primary key,
    private String sod_id;//    not null,
    private String oper;
    private String ch_field;
    private int registration;
    private int vydacha;
    private int tip_registr;
    private int izjat;
    private int zapros;
    private String commenta;
    private int oper_nic;
    private String osn_para;

    public OperSybase(){}
}
