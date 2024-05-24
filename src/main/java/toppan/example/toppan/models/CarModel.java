package toppan.example.toppan.models;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity(name = "model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CarMarka marka;
    private String model;

    public CarModel() {
    }
}

