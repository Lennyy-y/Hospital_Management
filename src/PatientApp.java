import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PatientApp extends App {
	public static void patientAppMenu(Scanner scanner, Connection connection) throws SQLException {
	    int id = -1;
	    boolean validIdFound = false;

	    while (!validIdFound) {
	        System.out.println("Please enter patient ID:");
	        int temp = scanner.nextInt();
	        
	        String query = "SELECT COUNT(*) FROM patient WHERE patient_id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setInt(1, temp);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                validIdFound = true;
	                id = temp;
	            } else {
	                System.out.println("Patient ID not found. Please try again.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    String patientQuery = "SELECT first_name, last_name FROM patient WHERE patient_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(patientQuery)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String firstName = rs.getString("first_name");
	            String lastName = rs.getString("last_name");
	            System.out.println("Welcome, " + firstName + " " + lastName + ".");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int choice = 0;
	    while (true) {
	        System.out.println("Please choose one of the following options:");
	        System.out.println("1. Make an Appointment\n2. View Appointments\n3. Return");
	        choice = scanner.nextInt();
	        switch (choice) {
	            case 1:
	                makeAppointment(scanner, connection, id);
	                break;
	            case 2:
	                viewAppointments(scanner, connection, id);
	                break;
	            case 3:
	                return;
	            default:
	                System.out.println("Invalid input, please try again.");
	        }
	    }
	}

	private static void makeAppointment(Scanner scanner, Connection connection, int id) throws SQLException {
		int doctorId;
		String appointmentDateStr, reason;

		System.out.print("Enter doctor ID: ");
		doctorId = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		do {
			System.out.print("Enter appointment date (YYYY-MM-DD): ");
			appointmentDateStr = scanner.nextLine();
			if (!SystemManager.isValidDate(appointmentDateStr)) {
				System.out.println("Invalid appointment date.");
			}
		} while (!SystemManager.isValidDate(appointmentDateStr));

		System.out.print("Enter reason for appointment: ");
		reason = scanner.nextLine();

		Date appointmentDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed = format.parse(appointmentDateStr);
			appointmentDate = new Date(parsed.getTime());
		} catch (ParseException e) {
			System.out.println("Error parsing appointment date.");
			e.printStackTrace();
		}

		String sqlAppointment = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, reason) VALUES (?, ?, ?, ?)";
		int appointmentId = -1;
		try (PreparedStatement pstmt = connection.prepareStatement(sqlAppointment)) {
			pstmt.setInt(1, id);
			pstmt.setInt(2, doctorId);
			pstmt.setDate(3, appointmentDate);
			pstmt.setString(4, reason);
			pstmt.executeUpdate();
			System.out.println("Appointment added successfully!");
		} catch (SQLException e) {
			System.out.println("Error adding appointment.");
			e.printStackTrace();
		}
	}

	
	public static void viewAppointments(Scanner scanner, Connection connection, int patientId) {
	    String query = "SELECT a.appointment_id, a.appointment_date, a.reason, " +
	                   "e.first_name AS doctor_first_name, e.last_name AS doctor_last_name " +
	                   "FROM appointment a " +
	                   "JOIN doctor d ON a.doctor_id = d.doctor_id " +
	                   "JOIN employee e ON d.employee_id = e.employee_id " +
	                   "WHERE a.patient_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, patientId);
	        ResultSet rs = pstmt.executeQuery();

	        boolean appointmentsFound = false;
	        while (rs.next()) {
	            appointmentsFound = true;
	            int appointmentId = rs.getInt("appointment_id");
	            String appointmentDate = rs.getString("appointment_date");
	            String reason = rs.getString("reason");
	            String doctorFirstName = rs.getString("doctor_first_name");
	            String doctorLastName = rs.getString("doctor_last_name");

	            System.out.println("Appointment ID: " + appointmentId);
	            System.out.println("Appointment Date: " + appointmentDate);
	            System.out.println("Reason: " + reason);
	            System.out.println("Doctor: " + doctorFirstName + " " + doctorLastName);
	            System.out.println("------------------------");
	        }

	        if (!appointmentsFound) {
	            System.out.println("No appointments found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error while searching for appointments.");
	    }
	}


}
