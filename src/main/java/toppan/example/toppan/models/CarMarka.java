package toppan.example.toppan.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "marka")
public class CarMarka {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String marka;

    @OneToMany(mappedBy = "marka")
    private List<CarModel> carModels;

    public CarMarka() {
    }

}
