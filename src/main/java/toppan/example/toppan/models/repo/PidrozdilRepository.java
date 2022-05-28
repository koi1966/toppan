package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import toppan.example.toppan.models.Pidrozdil;

import java.util.List;

public interface PidrozdilRepository extends CrudRepository<Pidrozdil,String> {

    List<Pidrozdil> findByOrderByPidrozdilAsc();

    Pidrozdil findByIp(String ip_user) ;

    @Query(nativeQuery = true,
            value = "SELECT email from pidrozdil where pidrozdil = :pidrozdil")
    String setEmailPidrozdil(@Param("pidrozdil") String pidrozdil) ;


    @Query("SELECT p.pidrozdil from Pidrozdil AS p order by p.pidrozdil DESC ")
    List<Pidrozdil> setPidrozdilAll() ;

}

