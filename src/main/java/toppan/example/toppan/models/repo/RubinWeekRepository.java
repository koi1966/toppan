package toppan.example.toppan.models.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Rubin_week;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RubinWeekRepository extends CrudRepository<Rubin_week, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM rubin_week r " +
                    "WHERE r.data_v >= :startDate " +
                    "AND r.data_v <= :endDate " +
                    "and r.pidrozdil = :tsc " +
                    "ORDER BY r.pidrozdil,r.data_v ")
    List<Rubin_week> setListDateRubinWeek(@Param("startDate") LocalDate data_v,
                                          @Param("endDate") LocalDate data_last,
                                          @Param("tsc") String tsc);

    List<Rubin_week> getAllByDataBetweenAndPidrozdilPidrozdil(LocalDate data, LocalDate data2, String pidrozdil, Sort sort);

    //   Помісячний звіт РСЦ
    //   обробка - null
//    SELECT coalesce(SUM(week_appeal),0) AS week_appeal FROM rubin_week WHERE data_v <= '01.10.2021'
    @Query(nativeQuery = true,
            value = "SELECT " +
                    "(SELECT coalesce(SUM(week_appeal),0) as week_appeal_old FROM rubin_week ri where ri.data_v >= :startDateOld and ri.data_v <= :endDateOld) as week_appeal_old," +
                    "(SELECT coalesce(SUM(week_issued),0) as week_issued_old FROM rubin_week ro where ro.data_v >= :startDateOld and ro.data_v <= :endDateOld) as week_issued_old, " +
                    "coalesce(SUM(week_appeal),0) week_appeal, " +
                    "coalesce(SUM(week_issued),0) as week_issued " +
                    "FROM rubin_week " +
                    "WHERE data_v >= :startDate " +
                    "AND data_v <= :endDate")
    String setRubinDate(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("startDateOld") LocalDate startDateOld,
                        @Param("endDateOld") LocalDate endDateOld);

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "(SELECT coalesce(SUM(week_appeal),0) as week_appeal_old FROM rubin_week ri where ri.data_v >= :startDateOld and ri.data_v <= :endDateOld and ri.pidrozdil = :tsc ) as week_appeal_old," +
                    "(SELECT coalesce(SUM(week_issued),0) as week_issued_old FROM rubin_week ro where ro.data_v >= :startDateOld and ro.data_v <= :endDateOld and ro.pidrozdil = :tsc ) as week_issued_old," +
                    "coalesce(SUM(week_appeal),0) as week_appeal, " +
                    "coalesce(SUM(week_issued),0) as week_issued, " +
                    "RIGHT (pidrozdil, 4) as pidrozdil " +
                    "FROM rubin_week " +
                    "WHERE data_v >= :startDate " +
                    "AND data_v <= :endDate " +
                    "and pidrozdil = :tsc " +
                    "GROUP BY pidrozdil")
    String getRubinDateTSC(@Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           @Param("startDateOld") LocalDate startDateOld,
                           @Param("endDateOld") LocalDate endDateOld,
                           @Param("tsc") String tsc);

    @Query(nativeQuery = true,
            value = "SELECT sum(week_appeal) as week_appeal, " +
                    "sum(week_issued) as week_issued, " +
                    "sum(week_appeal) + " +

                    "(SELECT year_appeal " +
                    "from rubin_year ry " +
                    "where ry.pidrozdil = :tsc " +
                    "and data_v = (select max(kk.data_v) " +
                    "from rubin_year kk " +
                    "where kk.pidrozdil = :tsc)) as year_appeal, " +

                    "sum(week_issued) + " +
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
            value = "SELECT " +
                    "coalesce(SUM(week_appeal),0) as week_appeal, " +
                    "coalesce(SUM(week_issued),0) as week_issued, " +
                    "(SELECT sum(ry.week_appeal) from rubin_week ry where ry.pidrozdil = :tsc and ry.data_v >= :firstYear and ry.data_v <= :endDate) as year_appeal, " +
                    "(SELECT sum(ri.week_issued) from rubin_week ri where ri.pidrozdil = :tsc and ri.data_v >= :firstYear and ri.data_v <= :endDate) as year_issued, " +
                    "pidrozdil " +
                    "from rubin_week " +
                    "where data_v >= :startDate " +
                    "and data_v <= :endDate " +
                    "and pidrozdil = :tsc " +
                    "group by pidrozdil")
    String setWeekPrint2022(@Param("startDate") LocalDate data_v,
                            @Param("endDate") LocalDate data_last,
                            @Param("firstYear") LocalDate firstYear,
                            @Param("tsc") String tsc);

    @Query(nativeQuery = true,
            value = "SELECT coalesce(SUM(week_appeal),0) as week_appeal, " +
                    "coalesce(SUM(week_issued),0) as week_issued, " +
                    "(SELECT sum(ry.week_appeal) from rubin_week ry where ry.data_v >= :firstYear and ry.data_v <= :endDate) as year_appeal, " +
                    "(SELECT sum(ri.week_issued) from rubin_week ri where ri.data_v >= :firstYear and ri.data_v <= :endDate) as year_issued " +
//                    "RIGHT(pidrozdil, 4) as pidrozdil " +
                    "from rubin_week " +
                    "where data_v >= :startDate " +
                    "and data_v <= :endDate ")
    String setWeekPrintRSC(@Param("startDate") LocalDate data_v,
                           @Param("endDate") LocalDate data_last,
                           @Param("firstYear") LocalDate firstYear);

    /**
     * МЕСЯЧНЫЙ ОТЧЕТ  Сумы нужных полей за период по нужному ТСЦ
     */
    @Query(nativeQuery = true,
            value = "SELECT coalesce(sum(ru.week_appeal),0) as week_appeal,coalesce(sum(ru.week_issued),0) as week_issued,RIGHT(ru.pidrozdil, 4) as pidrozdil " +
                    "from rubin_week ru where ru.data_v >= :start_Date and ru.data_v <= :end_Date and ru.pidrozdil = :tsc group by ru.pidrozdil")
    String setSumWeek(@Param("start_Date") Date start_Date,
                      @Param("end_Date") Date end_Date,
                      @Param("tsc") String tsc);

    @Query(nativeQuery = true,
            value = "SELECT r.id, r.pidrozdil,r.data_v, r.week_appeal, r.week_issued " +
                    "FROM rubin_week r " +
                    "WHERE r.data_v >= :start_Date " +
                    "AND r.data_v <= :end_Date " +
                    "ORDER BY pidrozdil,r.data_v ")
    List<Rubin_week> setWeekRSC(@Param("start_Date") LocalDate start_Date,
                                @Param("end_Date") LocalDate end_Date);

    @Query(nativeQuery = true,
            value = "SELECT coalesce(SUM(rw.week_appeal),0) as week_appeal,coalesce(SUM(rw.week_issued),0) as week_issued " +
                    "FROM rubin_week rw " +
                    "WHERE rw.data_v >= :start_Date " +
                    "AND rw.data_v <= :end_Date")
    String setWeekRSCSum(@Param("start_Date") LocalDate start_Date,
                         @Param("end_Date") LocalDate end_Date);

    @Query(nativeQuery = true,
            value = "SELECT coalesce(SUM(rw.week_appeal),0) as week_appeal,coalesce(SUM(rw.week_issued),0) as week_issued " +
                    "FROM rubin_week rw " +
                    "WHERE rw.data_v >= :start_Date " +
                    "AND rw.data_v <= :end_Date " +
                    "AND rw.pidrozdil = :tsc")
    String setWeekAllTSCSum(@Param("start_Date") LocalDate start_Date,
                            @Param("end_Date") LocalDate end_Date,
                            @Param("tsc") String tsc);

}