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
    List<Rubin_week> setListWeekDate(@Param("data_v") Date data_v);

    @Query(nativeQuery = true,
            value = "SELECT * FROM rubin_week r " +
                    "WHERE r.data_v >= :startDate " +
                    "AND r.data_v <= :endDate " +
                    "and r.pidrozdil = :tsc " +
                    "ORDER BY r.pidrozdil,r.data_v ")
    List<Rubin_week> setListDateRubinWeek(@Param("startDate") Date data_v,
                                          @Param("endDate") Date data_last,
                                          @Param("tsc") String tsc);
    //   Помісячний звіт РСЦ
    //   обработка null
//    SELECT coalesce(SUM(week_appeal),0) AS week_appeal FROM rubin_week WHERE data_v <= '01.10.2021'
//
    @Query(nativeQuery = true,
            value = "SELECT sum(week_appeal) as week_appeal, sum(week_issued) as week_issued, " +
                    "(SELECT coalesce(SUM(week_appeal),0) as week_appeal_old FROM rubin_week ri where ri.data_v >= :startDateOld and ri.data_v <= :endDateOld) as week_appeal_old," +
                    "(SELECT coalesce(SUM(week_issued),0) as week_issued_old FROM rubin_week ro where ro.data_v >= :startDateOld and ro.data_v <= :endDateOld) as week_issued_old "+
                    "FROM rubin_week " +
                    "WHERE data_v >= :startDate " +
                    "AND data_v <= :endDate")
    String setRubinDate(@Param("startDate") Date startDate,
                        @Param("endDate") Date endDate,
                        @Param("startDateOld") Date startDateOld,
                        @Param("endDateOld") Date endDateOld
    );


    //    Сумы нужніх полей за период по нужному ТСЦ
//    @Query(
////            coalesce(SUM(week_appeal),0) as week_appeal_old
//            value = "SELECT coalesce(sum(week_appeal),0) as week_appeal, " +
//                    "coalesce(sum(week_issued),0) as week_issued, " +
//                    "coalesce(sum(week_appeal),0) + " +
//                    "(SELECT coalesce(year_appeal,0) as year_appeal from rubin_year ry where ry.pidrozdil = :tsc and data_v = (select max(kk.data_v) from rubin_year kk where kk.pidrozdil = :tsc)) as year_appeal, " +
//                    "coalesce(sum(week_issued),0) + " +
//
//                    "(SELECT coalesce(year_issued,0) as year_issued from rubin_year ry " +
//                    "where ry.pidrozdil = :tsc " +
//                    "and data_v = (select max(kk.data_v) " +
//                    "from rubin_year kk where kk.pidrozdil = :tsc)) as year_issued " +
//                    "from rubin_week " +
//                    "where data_v >= :startDate " +
//                    "and data_v <= :endDate " +
//                    "and pidrozdil = :tsc")
//    String setWeekPrint(@Param("startDate") Date data_v,
//                        @Param("endDate") Date data_last,
//                        @Param("tsc") String tsc);


    @Query(nativeQuery = true,
            value = "SELECT sum(week_appeal) as week_appeal, " +
                    "sum(week_issued) as week_issued, " +
                    "sum(week_appeal) + " +
                    "(SELECT year_appeal " +
                    "from rubin_year ry " +
                    "where ry.pidrozdil = :tsc " +
                    "and data_v = (select max(kk.data_v) " +
                    "from rubin_year kk " +
                    "where kk.pidrozdil = :tsc)) as year_appeal, sum(week_issued) + " +
                    "(SELECT year_issued from rubin_year ry " +
                    "where ry.pidrozdil = :tsc " +
                    "and data_v = (select max(kk.data_v) " +
                    "from rubin_year kk where kk.pidrozdil = :tsc)) as year_issued, " +
                    "RIGHT(pidrozdil, 4) as pidrozdil " +
                    "from rubin_week " +
                    "where data_v >= :startDate " +
                    "and data_v <= :endDate " +
                    "and pidrozdil = :tsc " +
                    "group by pidrozdil")
    String setWeekPrint(@Param("startDate") Date data_v,
                        @Param("endDate") Date data_last,
                        @Param("tsc") String tsc);

    @Query(nativeQuery = true,
            value = "SELECT sum(week_appeal) as week_appeal, " +
                    "sum(week_issued) as week_issued, " +

                    "(SELECT sum(ry.week_appeal) from rubin_week ry where ry.pidrozdil = :tsc and ry.data_v >= :firstYear and ry.data_v <= :endDate) as year_appeal, " +


                    "(SELECT sum(ri.week_issued) from rubin_week ri where ri.pidrozdil = :tsc and ri.data_v >= :firstYear and ri.data_v <= :endDate) as year_issued, " +
                    "RIGHT(pidrozdil, 4) as pidrozdil " +
                    "from rubin_week " +
                    "where data_v >= :startDate " +
                    "and data_v <= :endDate " +
                    "and pidrozdil = :tsc " +
                    "group by pidrozdil")
    String setWeekPrint2022(@Param("startDate") Date data_v,
                        @Param("endDate") Date data_last,
                        @Param("firstYear") Date firstYear,
                        @Param("tsc") String tsc);

//    setWeekPrintRSC

    @Query(nativeQuery = true,
            value = "SELECT sum(week_appeal) as week_appeal, " +
                    "sum(week_issued) as week_issued, " +

                    "(SELECT sum(ry.week_appeal) from rubin_week ry where ry.data_v >= :firstYear and ry.data_v <= :endDate) as year_appeal, " +


                    "(SELECT sum(ri.week_issued) from rubin_week ri where ri.data_v >= :firstYear and ri.data_v <= :endDate) as year_issued " +
//                    "RIGHT(pidrozdil, 4) as pidrozdil " +
                    "from rubin_week " +
                    "where data_v >= :startDate " +
                    "and data_v <= :endDate ")
    String setWeekPrintRSC(@Param("startDate") Date data_v,
                            @Param("endDate") Date data_last,
                            @Param("firstYear") Date firstYear);

    //  МЕСЯЧНЫЙ ОТЧЕТ  Сумы нужных полей за период по нужному ТСЦ
    @Query(nativeQuery = true,
            value = "SELECT coalesce(sum(week_appeal),0) as week_appeal,coalesce(sum(week_issued),0) as week_issued,RIGHT(pidrozdil, 4) as pidrozdil " +
                    "from rubin_week ru where ru.data_v >= :start_Date and ru.data_v <= :end_Date and ru.pidrozdil = :tsc group by ru.pidrozdil")
    String setSumWeek(@Param("start_Date") Date start_Date, @Param("end_Date") Date end_Date, @Param("tsc") String tsc);

}
//
//    SELECT coalesce(SUM(week_appeal),0) AS week_appeal FROM rubin_week WHERE data_v <= '01.10.2021'
//
//      select RIGHT(pidrozdil, 4) from rubin_week