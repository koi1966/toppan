package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Arest;

import java.sql.Timestamp;

public interface ArestRepository extends CrudRepository<Arest, Long> {
    @Query("select count(*) from Arest a where a.data_sna is not null")
    long countDataSnaIsNotNull();

    @Query(nativeQuery = true,
            value = "select max(data_sna) as data_sna from arest where data_sna is not null")
    Timestamp maxDataSnaArestPostgres();

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE arest SET data_sna = :data_sna, commenta = :commenta, data_out = :data_out, k_data2 = :k_data2," +
                    " k_nom2 = :k_nom2, oper_out = :oper_out, out_nom = :out_nom, who_sha = :who_sha" +
                    " WHILE rtime_fix = :time_fix AND kart_id = :kart_id")
    void updateArest(@Param("data_sna") Timestamp data_sna,
                     @Param("time_fix") Timestamp time_fix,
                     @Param("kart_id") String kart_id,
                     @Param("commenta") String commenta,
                     @Param("data_out") Timestamp data_out,
                     @Param("k_data2") Timestamp k_data2,
                     @Param("k_data2") String k_nom2,
                     @Param("oper_out") String oper_out,
                     @Param("out_nom") String out_nom,
                     @Param("who_sha") String who_sha
    );
}

