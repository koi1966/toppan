package toppan.example.toppan.service;

import org.springframework.stereotype.Component;
import toppan.example.toppan.bl.DataDAOSybase;

import java.sql.*;

@Component
public class ArestDAOSybase {
    private final DataDAOSybase dataDAOSybase;
    private final Service service;

    public ArestDAOSybase(DataDAOSybase dataDAOSybase, Service service) {
        this.dataDAOSybase = dataDAOSybase;
        this.service = service;
    }

    public void findArestDataSna(Timestamp dataSnaPos) throws SQLException {

        final Connection connectionSa = dataDAOSybase.getConnectionSa();
        PreparedStatement preparedStatement = connectionSa.prepareStatement("SELECT * from arest WHERE data_sna > ? ORDER BY data_sna");
        preparedStatement.setObject(1, dataSnaPos);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            service.arestSybase(resultSet);
        }
        connectionSa.close();
    }

    public long countArestDataSna() throws SQLException {
        final Connection connectionSa = dataDAOSybase.getConnectionSa();
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
