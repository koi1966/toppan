package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Karta;

import java.util.List;

public interface KartaRepository extends JpaRepository<Karta, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM karta WHERE znak >= :znak")
    List<Karta> findByKartaZnak(@Param("znak") String znak);

}
