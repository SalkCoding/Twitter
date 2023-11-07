package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {

    private final static String ip = "127.0.0.1";
    private final static String dbName = "twitter";

    private final static String user = "root";
    private final static String password = "salk1104";

    private static Connection previousConnection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getNewConnection() {
        try {
            //Close previous connection
            if (previousConnection != null)
                previousConnection.close();

            return previousConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + "/" + dbName, user, password
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
