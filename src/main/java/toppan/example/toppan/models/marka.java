package toppan.example.toppan.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class marka {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String marka;

    public marka() {
    }

}
