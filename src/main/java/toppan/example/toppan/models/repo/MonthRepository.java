package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Rubin_month;

public interface MonthRepository extends CrudRepository<Rubin_month, Long> {

//       проверка на дубликат записи

    /**
     * @param month_year
     * @param pidrozdil
     * @return
     */
    @Query(nativeQuery = true,
            value = "select * from rubin_month where month_year = :month_year and pidrozdil = :pidrozdil ORDER BY pidrozdil")
    String setMonthPidrozdil(@Param("month_year") String month_year,
                             @Param("pidrozdil") String pidrozdil);
}
