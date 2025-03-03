import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "YOUR_DB_URL";
    private static final String USER = "YOUR_POSTGRES_USERNAME";
    private static final String PASSWORD = "YOUR_POSTGRES_PASSWORD";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}