package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import toppan.example.toppan.models.Arest;

import java.sql.Timestamp;

public interface ArestRepository extends CrudRepository<Arest, Long> {

    @Query(nativeQuery = true,
            value = "select max(data_sna) as data_sna from arest where data_sna is not null")
    Timestamp maxDataSnaArestPostgres();
}

