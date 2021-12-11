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

//    проверка на дубликат записи

    @Query(nativeQuery = true,
            value = "select * from rubin where data_v = :data_v and pidrozdil = :pidrozdil")
    String setDatePidrozdil(@Param("data_v") Date data_v,
                            @Param("pidrozdil") String pidrozdil);



    //    Сумы двух полей за период по всем ТСЦ
//    @Query(nativeQuery = true,
//            value = "select id, data_v, week, week_1, year_0 , year_1,pidrozdil from rubin
//                     where pidrozdil = 'ТСЦ 1841' and data_v = (select max(kk.data_v)
//                     from rubin kk where kk.id = rubin.id)")
//    String setSumDate(@Param("data_v") Date data_v);

    // ***************************************************************
    //  поэкспериментировал ..возможно пригодится для отчета..

//      select sum(week) as week0,
//          sum(week_1) as week_1,
//
//    sum(week) + (select r.year_0 from rubin r
//    where r.data_v = '2021-10-30'
//    and r.pidrozdil = rr.pidrozdil
//    GROUP BY r.pidrozdil, r.year_0) as year_0,
//
//    sum(week_1) + (select r1.year_1 from rubin r1
//    where r1.data_v = '2021-11-06'
//    and r1.pidrozdil = rr.pidrozdil
//    GROUP BY r1.pidrozdil, r1.year_1) as year_1,
//
//    pidrozdil
//
//    from rubin rr
//    where rr.data_v>='2021-11-01'
//    AND rr.data_v<='2021-11-30'
//    GROUP BY rr.pidrozdil
//    order by rr.pidrozdil;
  // ************************************

}
