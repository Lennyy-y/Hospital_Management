import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class DoctorApp extends App {

	public static void doctorAppMenu(Scanner scanner, Connection connection)
	{
	    int id = -1;
	    boolean validIdFound = false;

	    while (!validIdFound) {
	        System.out.println("Please enter doctor ID:");
	        int temp = scanner.nextInt();
	        
	        String query = "SELECT COUNT(*) FROM doctor WHERE doctor_id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setInt(1, temp);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                validIdFound = true;
	                id = temp;
	            } else {
	                System.out.println("Doctor ID not found. Please try again.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    String employeeQuery = "SELECT e.first_name, e.last_name FROM doctor d " +
	                           "JOIN employee e ON d.employee_id = e.employee_id " +
	                           "WHERE d.doctor_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(employeeQuery)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String firstName = rs.getString("first_name");
	            String lastName = rs.getString("last_name");
	            System.out.println("Welcome, Dr. " + firstName + " " + lastName + ".");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int choice = 0;
	    while(true)
	    {
	        System.out.println("Please choose one of the following options:");
		    System.out.println("1. Create Prescription\n2. Find Existing Prescription\n3. Return");
	    	choice = scanner.nextInt();
	    	switch(choice) {
	    	case 1:
	    		createPrescription(scanner, connection);
	    		break;
	    	case 2:
	    		findPrescription(scanner, connection);
	    		break;
	    	case 3:
	    		return;
	    	default:
	    		System.out.println("Invalid input, please try again.");
	    	}
	    }

	}
	
	public static void createPrescription(Scanner scanner, Connection connection) {
	    try {
	        System.out.println("Enter patient ID:");
	        int patientId = scanner.nextInt();

	        System.out.println("Enter medicine ID:");
	        int medicineId = scanner.nextInt();

	        System.out.println("Enter pharmacist ID:");
	        int pharmacistId = scanner.nextInt();

	        System.out.println("Enter dosage in milligrams:");
	        int dosage = scanner.nextInt();
	        scanner.nextLine();  // Consume the newline character

	        System.out.println("Enter frequency:");
	        String frequency = scanner.nextLine();

	        String insertQuery = "INSERT INTO prescription (patient_id, medicine_id, pharmacist_id, dosage, frequency) VALUES (?, ?, ?, ?, ?)";

	        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
	            pstmt.setInt(1, patientId);
	            pstmt.setInt(2, medicineId);
	            pstmt.setInt(3, pharmacistId);
	            pstmt.setString(4, Integer.toString(dosage));
	            pstmt.setString(5, frequency);

	            pstmt.executeUpdate();
	            System.out.println("Prescription created successfully.");
	        } catch (SQLException e) {
	            System.err.println("Error creating prescription: " + e.getMessage());
	            System.out.println("The prescription was not created.");
	            return;
	        }
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        System.out.println("Invalid input. The prescription was not created.");
	        return;
	    }
	}
	
	
	public static void findPrescription(Scanner scanner, Connection connection) {
	    System.out.println("Enter prescription ID:");
	    int prescriptionId = scanner.nextInt();

	    String query = "SELECT p.dosage, p.frequency, " +
	                   "pt.first_name AS patient_first_name, pt.last_name AS patient_last_name, " +
	                   "m.name AS medicine_name, " +
	                   "e.first_name AS pharmacist_first_name, e.last_name AS pharmacist_last_name " +
	                   "FROM prescription p " +
	                   "JOIN patient pt ON p.patient_id = pt.patient_id " +
	                   "JOIN medicine m ON p.medicine_id = m.medicine_id " +
	                   "JOIN pharmacist ph ON p.pharmacist_id = ph.pharmacist_id " +
	                   "JOIN employee e ON ph.employee_id = e.employee_id " +
	                   "WHERE p.prescription_id = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, prescriptionId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String patientFirstName = rs.getString("patient_first_name");
	            String patientLastName = rs.getString("patient_last_name");
	            String medicineName = rs.getString("medicine_name");
	            String pharmacistFirstName = rs.getString("pharmacist_first_name");
	            String pharmacistLastName = rs.getString("pharmacist_last_name");
	            String dosage = rs.getString("dosage");
	            String frequency = rs.getString("frequency");

	            System.out.println("Prescription Details:");
	            System.out.println("Patient: " + patientFirstName + " " + patientLastName);
	            System.out.println("Medicine: " + medicineName);
	            System.out.println("Pharmacist: " + pharmacistFirstName + " " + pharmacistLastName);
	            System.out.println("Dosage: " + dosage);
	            System.out.println("Frequency: " + frequency);
	        } else {
	            System.out.println("Prescription ID " + prescriptionId + " not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error while searching for the prescription.");
	    }
	}
}
