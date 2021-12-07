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
    List<Rubin_week> setListDateRubin(@Param("data_v") Date data_v);

    @Query(nativeQuery = true,
            value = "SELECT * FROM rubin_week r WHERE r.data_v >= :data_v AND r.data_v <= :data_last and r.pidrozdil = :tsc ORDER BY r.pidrozdil,r.data_v ")
    List<Rubin_week> setListDateRubinWeek(@Param("data_v") Date data_v,
                                          @Param("data_last") Date data_last,
                                          @Param("tsc") String tsc);

    @Query(nativeQuery = true,
            value = "select sum(week) as week, sum(week_1) as week_1, sum(year_0) as year_0 , sum(year_1) as year_1 from rubin where data_v = :data_v")
    String setSumDate(@Param("data_v") Date data_v);
}
