import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PatientDataManager {
	public static void patientDataMenu(Scanner scanner, Connection connection) throws SQLException {
		while (true) {
			System.out.println("\n=== Hospital Database System Menu ===");
			System.out.println("1. Manage Appointments");
			System.out.println("2. Manage Medications");
			System.out.println("3. Manage Billing");
			System.out.println("4. View Patient History");
			System.out.println("5. Return to Main Menu");
			System.out.print("Select an option (1-5): ");

			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				manageAppointments(scanner, connection);
				break;
			case 2:
				manageMedications(scanner, connection);
				break;
			case 3:
				manageBilling(scanner, connection);
				break;
			case 4:
				viewPatientHistory(scanner, connection);
				break;
			case 5:
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void manageAppointments(Scanner scanner, Connection connection) throws SQLException {
		System.out.println("\n=== Manage Appointments ===");
		System.out.println("1. View Upcoming Appointments");
		System.out.println("2. View Appointment History");
		System.out.print("Select an option (1-2): ");

		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			viewUpcomingAppointments(scanner, connection);
			break;
		case 2:
			viewAppointmentHistory(scanner, connection);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
	}

	private static void manageMedications(Scanner scanner, Connection connection) throws SQLException {
		System.out.println("\n=== Manage Medications ===");
		System.out.println("1. View Drugs Prescribed");
		System.out.println("2. View Medication History");
		System.out.print("Select an option (1-2): ");

		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			viewDrugsPrescribed(scanner, connection);
			break;
		case 2:
			viewMedicationHistory(scanner, connection);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
	}

	private static void manageBilling(Scanner scanner, Connection connection) throws SQLException {
		System.out.println("\n=== Manage Billing ===");
		System.out.println("1. Calculate Total Medical Bill");
		System.out.print("Select an option (1): ");

		int choice = scanner.nextInt();
		if (choice == 1) {
			calculateTotalBill(scanner, connection);
		} else {
			System.out.println("Invalid option. Please try again.");
		}
	}

	private static void viewPatientHistory(Scanner scanner, Connection connection) throws SQLException {
		System.out.println("\n=== View Patient History ===");
		System.out.println("1. View Full Appointment History");
		System.out.println("2. View Full Medication History");
		System.out.print("Select an option (1-2): ");

		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			viewFullAppointmentHistory(scanner, connection);
			break;
		case 2:
			viewFullMedicationHistory(scanner, connection);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
	}

	private static void viewUpcomingAppointments(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT a.appointment_id, a.appointment_date, "
				+ "e.first_name || ' ' || e.last_name AS doctor_name, a.reason " + "FROM appointment a "
				+ "JOIN doctor d ON a.doctor_id = d.doctor_id " + "JOIN employee e ON d.employee_id = e.employee_id "
				+ "WHERE a.patient_id = ? AND appointment_date > CURRENT_DATE";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nUpcoming Appointments:");
		while (rs.next()) {
			System.out.printf("Date: %s, Doctor Name: %s, Reason: %s%n", rs.getDate("appointment_date"),
					rs.getString("doctor_name"), rs.getString("reason"));
		}
	}

	private static void viewAppointmentHistory(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT a.appointment_id, a.appointment_date, "
				+ "e.first_name || ' ' || e.last_name AS doctor_name, a.reason " + "FROM appointment a "
				+ "JOIN doctor d ON a.doctor_id = d.doctor_id " + "JOIN employee e ON d.employee_id = e.employee_id "
				+ "WHERE a.patient_id = ? <= CURRENT_DATE";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nAppointment History:");
		while (rs.next()) {
			System.out.printf("Date: %s, Doctor Name: %s, Reason: %s%n", rs.getDate("appointment_date"),
					rs.getString("doctor_name"), rs.getString("reason"));
		}
	}

	private static void viewDrugsPrescribed(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT m.name AS medicine_name, p.dosage, p.frequency " + "FROM prescription p "
				+ "JOIN medicine m ON p.medicine_id = m.medicine_id " + "WHERE p.patient_id = ?";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nDrugs Prescribed:");
		while (rs.next()) {
			System.out.printf("Medicine Name: %s, Dosage: %d mg, Frequency: %s%n", rs.getString("medicine_name"),
					rs.getInt("dosage"), rs.getString("frequency"));
		}
	}

	private static void viewMedicationHistory(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT m.name AS medicine_name, p.dosage, p.frequency " + "FROM prescription p "
				+ "JOIN medicine m ON p.medicine_id = m.medicine_id " + "WHERE p.patient_id = ?";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nMedication History:");
		while (rs.next()) {
			System.out.printf("Medicine Name: %s, Dosage: %d mg, Frequency: %s%n", rs.getString("medicine_name"),
					rs.getInt("dosage"), rs.getString("frequency"));
		}
	}

	private static void calculateTotalBill(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT SUM(pi.price) AS total_bill " + "FROM prescription p "
				+ "JOIN pharmacy_inventory pi ON p.medicine_id = pi.medicine_id " + "WHERE p.patient_id = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			System.out.printf("Total Medical Bill: $%.2f%n", rs.getDouble("total_bill"));
		} else {
			System.out.println("No billing information found for the patient.");
		}
	}

	private static void viewFullAppointmentHistory(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT a.appointment_id, a.appointment_date, "
				+ "e.first_name || ' ' || e.last_name AS doctor_name, a.reason " + "FROM appointment a "
				+ "JOIN doctor d ON a.doctor_id = d.doctor_id " + "JOIN employee e ON d.employee_id = e.employee_id "
				+ "WHERE a.patient_id = ?";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nFull Appointment History:");
		while (rs.next()) {
			System.out.printf("Date: %s, Doctor Name: %s, Reason: %s%n", rs.getDate("appointment_date"),
					rs.getString("doctor_name"), rs.getString("reason"));
		}
	}

	private static void viewFullMedicationHistory(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Enter patient ID: ");
		int patientId = scanner.nextInt();
		String query = "SELECT m.name AS medicine_name, p.dosage, p.frequency " + "FROM prescription p "
				+ "JOIN medicine m ON p.medicine_id = m.medicine_id " + "WHERE p.patient_id = ?";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, patientId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("\nFull Medication History:");
		while (rs.next()) {
			System.out.printf("Medicine Name: %s, Dosage: %d mg, Frequency: %s%n", rs.getString("medicine_name"),
					rs.getInt("dosage"), rs.getString("frequency"));
		}
	}
}
