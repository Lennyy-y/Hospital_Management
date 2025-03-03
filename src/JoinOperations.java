import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class JoinOperations {

    public static void executeSQLQuery(String query) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            prettyPrintResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prettyPrintResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Print column names
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(String.format("%-35s", metaData.getColumnName(i)));
        }
        System.out.println();

        // Print separator
        for (int i = 1; i <= columnCount; i++) {
            System.out.print("-----------------------------------");
        }
        System.out.println();

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("%-35s", rs.getString(i)));
            }
            System.out.println();
        }
    }
}
