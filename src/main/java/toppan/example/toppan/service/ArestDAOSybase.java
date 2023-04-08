package toppan.example.toppan.service;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static toppan.example.toppan.bl.DataDAOSybase.connectionSa;

@Component
public class ArestDAOSybase {

    private final ArestDAOPostgres arestDAOPostgres;
    private final Service service;
    public ArestDAOSybase(KartaDAOPostgres kartaDAOPostgres, ArestDAOPostgres arestDAOPostgres, Service service) {

        this.arestDAOPostgres = arestDAOPostgres;
        this.service = service;
    }

    public void findArestDataSna(Timestamp dataSnaPos) throws SQLException {

        PreparedStatement preparedStatement = connectionSa.prepareStatement("SELECT * from arest WHERE data_sna > ?");
        preparedStatement.setObject(1, dataSnaPos);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                arestDAOPostgres.updateArest(service.arestSybase(resultSet));
            }
        }

    }

}
