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
            value = "select sum(week) as week, sum(week_1) as week_1, sum(year_0) as year_0 , sum(year_1) as year_1 from rubin where data_v = :data_v")
    String setSumDate(@Param("data_v") Date data_v);


}
