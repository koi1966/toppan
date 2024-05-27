# toppan
Statistics and analysis of the use of the Toppan CP500 printer.

I am learning java and deepening my knowledge.

I have experience working with Oracle, Sybase databases, I am learning Mongodb
I decided to switch to Java. I'm currently employed, but I'm aiming to switch from administration (which I've been doing for the past 7 years) to Java programming. As a Java developer, I learn, and the best way to learn is through project implementation. I came up with three projects to explore databases.

The first test project (https://github.com/koi1966/test.database.sybase) - training with - statement = connectionSa.createStatement with Tomcat and our Sybase Adaptive Server Enterprise.

I am implementing the second project (toppan - https://github.com/koi1966/toppan ) - automating some reports on educational work / using - spring-boot, hibernate, postgresql with sending the report to Email. Finished working project, improvement .. correction of the class created by me according to the principles of class design (S.O.L.I.D.)

I have no commercial experience as a Java developer, but I have the desire and I will not let you down.

For training I use IntelliJ IDEA 2021.3.1 (Ultimate Edition)


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
Готовий працюючий проєкт, покращую .. виправляю створене мною за принципиам дизайну класів (S.O.L.I.D.)

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

28.18
