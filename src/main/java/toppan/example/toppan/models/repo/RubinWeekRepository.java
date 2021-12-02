package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Rubin_week;

import java.util.Date;
import java.util.List;

public interface RubinWeekRepository extends CrudRepository<Rubin_week, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * from rubin_week r where r.data_v = :data_v ORDER BY r.pidrozdil")
    List<Rubin_week> setListDateRubin(@Param("data_v") Date data_v) ;
}
