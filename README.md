# toppan
Statistics and analysis of the use of the Toppan CP500 printer.

Вивчаю java, поглиблюю зання..

Маю досвід по роботі з Pascal та базами даних Sybase. 
Вирішив перепрофілюватись на Java.
На даний час я працевлаштований, але маю мету зінити напрямок роботи з адміністрування (яким я займаюсь останні 7років ) 
на програмуваня в Java.
Як Java розробник я навчаюсь, а краще навчання - виконувати проєкт. 
Я вигадав для себе три проєкти для навчаня з використанням баз даних. 

Перший проєкт пробний (https://github.com/koi1966/test.database.sybase) - 
вивчання з використнням - statement = connectionSa.createStatement 
з Tomcat та працюючою в нас Sybase Adaptive Server Enterpeyse.

Реалізвую другий проєкт (toppan - https://github.com/koi1966/toppan ) - aвтоматизую деякі звітності по роботі 
вивчанню/використовую - spring-boot, hibernate, postgresql з відправкою звіту на Email. 
Готовий працюючий проєкт, покращую .. виправляю створене мною за принципами (S.O.L.I.D.)

Комерційний досвід як Java розробника відсутній але я маю бажання і не підведу.

Для навчання я використовую IntelliJ IDEA 2021.3.1 (Ultimate Edition)

// ====================
select karta.*
from karta
where not exists (
select 1
from karta2
where karta2.id = karta.id);
// ====================
INSERT INTO marka (id, marka)
SELECT ROW_NUMBER() OVER(ORDER BY karta.marka ASC) AS id, karta.marka
FROM karta
group by karta.marka;
// ====================

47.18