package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Arest;

import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

//JpaRepository
//CrudRepository
public interface ArestRepository extends CrudRepository<Arest, Long> {
    @Query("select count(*) from Arest a where a.data_sna is not null")
    long countDataSnaIsNotNull();

    @Query(
            value = "select max(data_sna) as data_sna from Arest where data_sna is not null")
    Timestamp maxDataSnaArestPostgres();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE arest SET data_sna = :data_sna, commenta = :commenta, data_out = :data_out, k_data2 = :k_data2," +
                    " k_nom2 = :k_nom2, oper_out = :oper_out, out_nom = :out_nom, who_sha = :who_sha, ish_answer1= :ish_answer1," +
                    " ish_answer2 = :ish_answer2" +
                    " WHERE time_fix = :time_fix AND kart_id = :kart_id")
    int updateArest(@Param("data_sna") Timestamp data_sna,
                    @Param("time_fix") Timestamp time_fix,
                    @Param("kart_id") String kart_id,
                    @Param("commenta") String commenta,
                    @Param("data_out") Timestamp data_out,
                    @Param("k_data2") Timestamp k_data2,
                    @Param("k_nom2") String k_nom2,
                    @Param("oper_out") String oper_out,
                    @Param("out_nom") String out_nom,
                    @Param("who_sha") String who_sha,
                    @Param("ish_answer1") Integer ish_answer1,
                    @Param("ish_answer2") Integer ish_answer2
    );

    @Query(nativeQuery = true,
            value = "SELECT * from Arest WHERE time_fix = :time_fix AND kart_id = :kart_id")
    List<Arest> findArest(@Param("time_fix") Timestamp time_fix,
                   @Param("kart_id") String kart_id
    );

}

