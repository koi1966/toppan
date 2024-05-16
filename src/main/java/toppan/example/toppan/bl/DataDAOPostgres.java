package toppan.example.toppan.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

//@Component
public class DataDAOPostgres {

        private static final String URL = "jdbc:postgresql://localhost:5432/rsc1840";
    private static final String URL2 = "jdbc:postgresql://localhost:5433/rsc1840";
//    private static final String URL = "jdbc:postgresql://10.6.1.1:5432/rsc1840";
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

            LocalDate today = LocalDate.now();
            LocalDate dateEnd = LocalDate.of(2024, 10, 8);

            if (today.isAfter(dateEnd) ) {
                System.out.println("connectionPostgresql - Ok");
                connectionPos = DriverManager.getConnection(URL2, USERNAME, PASSWORD);
                }
            else {
                connectionPos = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("connectionPos-Ok");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("connectionPos-Error");
        }
    }

}
