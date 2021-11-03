package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Pidrozdil;

public interface PidrozdilRepository extends CrudRepository<Pidrozdil,String> {

    @Query(nativeQuery = true,
            value = "SELECT pidrozdil from pidrozdil p where p.ip = :ip_user")
            String setNamePidrozdil(@Param("ip_user") String ip_user) ;
}


//    Вам просто нужно установить необходимые свойства для сущности и передать ее методу findAll (Entity entity)
//
//    Например, если вам нужны сотрудники с некоторым значением свойства, установите для него эти параметры и передайте их в базу данных запросов.
//
//            Employee employee = new Employee ();
//employee. setAge (35);
//employee. setExperience(5);
//    Теперь передайте его для запроса
//
//    List <Employee> employèes = do. findAll (Employee);
//


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


//public interface RubinRepository extends CrudRepository<Rubin, Long> {
//
//}

