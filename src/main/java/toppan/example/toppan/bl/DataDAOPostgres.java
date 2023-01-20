package toppan.example.toppan.bl;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataDAOPostgres {
    //    private static final String URL = "jdbc:postgresql://localhost:5432/rsc1840";
    private static final String URL = "jdbc:postgresql://10.6.1.1:5432/rsc1840";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1305196613051966";

//    private static final String URL = "jdbc:postgresql://localhost:5432/rsc1840";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "postgres";

    public static Connection connectionPos;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connectionPos = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connectionPos-Ok");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("connectionPos-Error");
        }
    }

}
