import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

	public static void signIn(Scanner scanner, Connection connection) throws SQLException
	{
		System.out.println("Please choose user type:\n1) Patient\n2) Doctor");
		int choice = scanner.nextInt();
		switch (choice) { 
		case 1:
			PatientApp.patientAppMenu(scanner, connection);
			break;
		case 2:
			DoctorApp.doctorAppMenu(scanner, connection);
			break;
		default:
			System.out.println("Invalid input, try again.");
			
		}
	}
}
