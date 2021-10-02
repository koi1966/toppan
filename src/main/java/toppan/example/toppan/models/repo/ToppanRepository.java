package toppan.example.toppan.models.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.toppan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

public interface ToppanRepository extends CrudRepository<toppan,String> {
//    @Query(nativeQuery = true,
//            value = "select * from toppan t where t.sn = :sn")
//
//    Stream findAllRecodToppan(@Param("sn") String sn);

// ===============================================================================

//    @Query(
//            nativeQuery = true,
//            value =
//                    "SELECT SUM(log.amount_billed)"
//                            + " FROM srx.billing_log log where log.created_at BETWEEN :from AND :to AND log.ship_to_id = :shipToId AND log.id NOT IN (:ids)")
//
//    BigDecimal calculateBillingLogByCreatedAtBetween(
//            @Param("from") LocalDateTime from,
//            @Param("to") LocalDateTime to,
//            @Param("shipToId") Long shipToId,
//            @Param("ids") Collection<Long> ids);
}

