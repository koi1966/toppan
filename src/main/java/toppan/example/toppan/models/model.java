package toppan.example.toppan.models;

import lombok.Data;

import javax.persistence.*;

@Data
    @Entity
    public class model {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private Long marka_id;
        private String model;

        public model() {
        }
}
