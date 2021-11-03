package toppan.example.toppan.models.repo;


import org.springframework.data.repository.CrudRepository;
import toppan.example.toppan.models.Toppan;

public interface ToppanRepository extends CrudRepository<Toppan, String> {

//    @Query(nativeQuery = true, //            value = "select * from toppan t where t.sn = :sn")
//    List findByEmailReturnStream(@Param("sn") String toppan);




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
}

//      Лучший способ выучить Java и стать Java-разработчиком:
//        1) Читать Герберта Шилдта "Java 10: Руководство для начинающих", но сильно не зацикливаться на книжке, а больше практике уделять на JavaRush
//        2) Делать задания на JavaRush (около 2к рублей в месяц подписка стоит, но лучше сразу на полгода взять) - лучший сайт для новичков (сам им пользовался в начале)
//        3) Курс "Java для начинающих" от Alishev - на ютубу целый плейлист (великолепный курс)
//        4) После всего курса выше переходи к изучению Spring Core - у Alishev на канале есть целый курс
//        5) Потом переходи к изучению Spring Boot, Hibernate ,параллельно учи PostgreSQL, Git, делая мини проект на Spring Boot + Hibernate (этот курс как раз про это и был)
//        6) Не забывай об алгоритмах и структурах данных, для этого есть книга Роберта Седжвика - "Алгоритмы на Java"
//        7) Практикуй алгоритмы и структурах данных на сайте HackerRank
//        8)  Заведи профиль на GitHub с твоими проектами
//        9) Сделай резюме и выложи его на агрегатор вакансий
//        10) Запасись терпением, это будет реально сложный путь ≈ 6-12 месяцев, в зависимости от твоего уровня подготовки. Успехов!

