package toppan.example.toppan.service;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static toppan.example.toppan.bl.DataDAOSybase.connectionSa;

@Component
public class ArestDAOSybase {

    private final Service service;

    public ArestDAOSybase( Service service) {

        this.service = service;
    }

    public void findArestDataSna(Timestamp dataSnaPos) throws SQLException {

        PreparedStatement preparedStatement = connectionSa.prepareStatement("SELECT * from arest WHERE data_sna > ? ORDER BY data_sna");
        preparedStatement.setObject(1, dataSnaPos);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            service.arestSybase(resultSet);
//            arestDAOPostgres.updateArest();
        }

    }

    public long countArestDataSna() throws SQLException {
        long coun = 0;
        PreparedStatement preparedStatement = connectionSa.prepareStatement("select count(*) as coun from arest where data_sna is not null");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
//            arestDAOPostgres.updateArest();
           coun = resultSet.getLong("coun");
        }
        return coun;
    }



}
