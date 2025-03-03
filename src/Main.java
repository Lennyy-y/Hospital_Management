import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        try {
			Connection connection = DatabaseConnection.getConnection(); {
            System.out.println("Connected to the PostgreSQL server successfully.");

            do {
                System.out.println("Hospital Management System");
                System.out.println("1. Patient Data Tracking");
                System.out.println("2. System Manager (Add/Remove)");
                System.out.println("3. Query Tool");
                System.out.println("4. User App (Doctors and Patients)");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        PatientDataManager.patientDataMenu(scanner, connection);
                        break;
                    case 2:
                        SystemManager.systemManagerMenu(scanner, connection);
                        break;
                    case 3:
                        ConsoleUI.runQueryTool(scanner, connection);
                        break;
                    case 4:
                    	App.signIn(scanner, connection);
                    	break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);

        }
        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

        scanner.close();
    }

}