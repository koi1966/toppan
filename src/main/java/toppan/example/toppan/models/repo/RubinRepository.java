package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Rubin;

import java.util.Date;
import java.util.List;


public interface RubinRepository extends CrudRepository<Rubin, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * from rubin r where r.data_v = :data_v ORDER BY r.pidrozdil")
    List<Rubin> setListDateRubin(@Param("data_v") Date data_v) ;

//    Сумы двух полей за период по всем ТСЦ
    @Query(nativeQuery = true,
            value = "select sum(week) as week, sum(week_1) as week_1 from rubin where r.data_v > :data_v and r.data_v <= :data_k ORDER BY r.data_r")
    List<Rubin> setSumDate(@Param("data_v") Date data_n, @Param("data_k") Date data_k );

//    year, year_1,


}
