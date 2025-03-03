import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemManager {

	public static void systemManagerMenu(Scanner scanner, Connection connection) throws SQLException {
		while (true) {
			System.out.println("\n=== System Manager Menu ===");
			System.out.println("1.	Add Entities to Database");
			System.out.println("2.	Delete Entities from Database");
			System.out.println("3.	Return to Main Menu");
			System.out.print("Select an option (1-3): ");

			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				scanner.nextLine();
				insertionMenu(scanner, connection);
				break;
			case 2:
				deletionMenu(scanner, connection);
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}
	
	public static void insertionMenu(Scanner scanner, Connection connection) throws SQLException {
		while (true) {
			System.out.println("\n=== System Manager Menu ===");
			System.out.println("1.	Add New Address");
			System.out.println("2.	Add New Appointment");
			System.out.println("3.	Add New Department");
			System.out.println("4.	Add New Doctor");
			System.out.println("5.	Add New Employee");
			System.out.println("6.	Add New Medicine");
			System.out.println("7.	Add New Patient");
			System.out.println("8.	Add New Pharmacist");
			System.out.println("9.	Add New Pharmacy Inventory Item");
			System.out.println("10.	Add New Prescription");
			System.out.println("11.	Return to Main Menu");
			System.out.print("Select an option (1-11): ");

			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				scanner.nextLine();
				addNewAddress(scanner, connection);
				break;
			case 2:
				addNewAppointment(scanner, connection);
				break;
			case 3:
				addNewDepartment(scanner, connection);
				break;
			case 4:
				addNewDoctor(scanner, connection);
				break;
			case 5:
				addNewEmployee(scanner, connection);
				break;
			case 6:
				addNewMedicine(scanner, connection);
				break;
			case 7:
				addNewPatient(scanner, connection);
				break;
			case 8:
				addNewPharmacist(scanner, connection);
				break;
			case 9:
				addNewPharmacyInventoryItem(scanner, connection);
				break;
			case 10:
				addNewPrescription(scanner, connection);
				break;
			case 11:
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}
	public static void deletionMenu(Scanner scanner, Connection connection) throws SQLException {
		while (true) {
			System.out.println("\n=== System Manager Menu ===");
			System.out.println("1.	Delete Address");
			System.out.println("2.	Delete Appointment");
			System.out.println("3.	Delete Department");
			System.out.println("4.	Delete Doctor");
			System.out.println("5.	Delete Employee");
			System.out.println("6.	Delete Medicine");
			System.out.println("7.	Delete Patient");
			System.out.println("8.	Delete Pharmacist");
			System.out.println("9.	Delete Pharmacy Inventory Item");
			System.out.println("10.	Delete Prescription");
			System.out.println("11.	Return to Main Menu");
			System.out.print("Select an option (1-11): ");
			int idChoice = -1;
			int choice = scanner.nextInt();
			if (choice > 0 && choice < 11)
			{
			EntityType entity = EntityType.fromId(choice);
			String entityStr = entity.toString();
			System.out.println("Enter " + entityStr + " ID:");
			idChoice = scanner.nextInt();
			}

			switch (choice) {
			case 1:
				scanner.nextLine();
				deleteAddress(connection, idChoice);
				break;
			case 2:
				deleteAppointment(connection, idChoice);
				break;
			case 3:
				deleteDepartment(connection, idChoice);
				break;
			case 4:
				deleteDoctor(connection, idChoice);
				break;
			case 5:
				deleteEmployee(connection, idChoice);
				break;
			case 6:
				deleteMedicine(connection, idChoice);
				break;
			case 7:
				deletePatient(connection, idChoice);
				break;
			case 8:
				deletePharmacist(connection, idChoice);
				break;
			case 9:
				deletePharmacyInventory(connection, idChoice);
				break;
			case 10:
				deletePrescription(connection, idChoice);
				break;
			case 11:
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static int addNewAddress(Scanner scanner, Connection connection) throws SQLException {
		String city, street, postalCode;
		System.out.print("Enter city: ");
		city = scanner.nextLine();
		System.out.print("Enter street and number: ");
		street = scanner.nextLine();
		System.out.print("Enter postal code: ");
		postalCode = scanner.nextLine();
		String sqlAddress = "INSERT INTO address (city, street, zip_code) VALUES (?, ?, ?)";

		int addressId = -1;
		try (PreparedStatement pstmtAddress = connection.prepareStatement(sqlAddress,
				Statement.RETURN_GENERATED_KEYS)) {
			pstmtAddress.setString(1, city);
			pstmtAddress.setString(2, street);
			pstmtAddress.setString(3, postalCode);
			pstmtAddress.executeUpdate();

			try (ResultSet generatedKeys = pstmtAddress.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					addressId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating address failed, no ID obtained.");
				}
				System.out.println("Address added successfully!");
			} catch (SQLException e) {
				System.out.println("Error adding address.");
				e.printStackTrace();
			}
		}
		return addressId;
	}

	private static void addNewAppointment(Scanner scanner, Connection connection) throws SQLException {
		int patientId, doctorId;
		String appointmentDateStr, reason;
		scanner.nextLine();

		System.out.print("Enter patient ID: ");
		patientId = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		System.out.print("Enter doctor ID: ");
		doctorId = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		do {
			System.out.print("Enter appointment date (YYYY-MM-DD): ");
			appointmentDateStr = scanner.nextLine();
			if (!isValidDate(appointmentDateStr)) {
				System.out.println("Invalid appointment date.");
			}
		} while (!isValidDate(appointmentDateStr));

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
		try (PreparedStatement pstmt = connection.prepareStatement(sqlAppointment)) {
			pstmt.setInt(1, patientId);
			pstmt.setInt(2, doctorId);
			pstmt.setDate(3, appointmentDate);
			pstmt.setString(4, reason);
			pstmt.executeUpdate();
			}
		System.out.println("Appointment added successfully.");
	}

	private static void addNewDepartment(Scanner scanner, Connection connection) throws SQLException {
		String name;
		scanner.nextLine();
		System.out.print("Enter department name: ");
		name = scanner.nextLine();

		String sqlDepartment = "INSERT INTO department (name) VALUES (?)";
		int departmentId = -1;
		try (PreparedStatement pstmt = connection.prepareStatement(sqlDepartment)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
				System.out.println("Department added successfully!");
			} catch (SQLException e) {
				System.out.println("Error adding department.");
				e.printStackTrace();
			}
		}

	private static int addNewDoctor(Scanner scanner, Connection connection) throws SQLException {
		int employeeId = addNewEmployee(scanner, connection);

		System.out.print("Enter department ID: ");
		int departmentId = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		String specialty = null;
		String sqlFetchDepartment = "SELECT name FROM department WHERE department_id = ?";
		try (PreparedStatement pstmtFetch = connection.prepareStatement(sqlFetchDepartment)) {
			pstmtFetch.setInt(1, departmentId);
			try (ResultSet rs = pstmtFetch.executeQuery()) {
				if (rs.next()) {
					specialty = rs.getString("name");
				} else {
					System.out.println("Department ID not found.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error fetching department name.");
			e.printStackTrace();
		}
		int doctorId = -1;
		String sqlDoctor = "INSERT INTO doctor (employee_id, specialty, department_id) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sqlDoctor, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, employeeId);
			pstmt.setString(2, specialty);
			pstmt.setInt(3, departmentId);
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					doctorId = generatedKeys.getInt(1);
					System.out.println("Doctor added successfully with ID: " + doctorId);
				} else {
					throw new SQLException("Creating doctor failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error adding doctor.");
			e.printStackTrace();
		}
		return doctorId;
	}

	private static int addNewEmployee(Scanner scanner, Connection connection) throws SQLException {
		String firstName, lastName, dobStr, phoneNum, email;
		scanner.nextLine();
		System.out.print("Enter first name: ");
		firstName = scanner.nextLine();
		System.out.print("Enter last name: ");
		lastName = scanner.nextLine();
		do {
			System.out.print("Enter date of birth (YYYY-MM-DD): ");
			dobStr = scanner.nextLine();
			if (!isValidDate(dobStr)) {
				System.out.println("Invalid date of birth");
			}
		} while (!isValidDate(dobStr));
		do {
			System.out.print("Enter phone number: ");
			phoneNum = scanner.nextLine();
			if (!isValidPhoneNumber(phoneNum)) {
				System.out.println("Invalid phone number");
			}
		} while (!isValidPhoneNumber(phoneNum));
		do {
			System.out.print("Enter email: ");
			email = scanner.nextLine();
			if (!isValidEmail(email)) {
				System.out.println("Invalid email");
			}
		} while (!isValidEmail(email));
		int addressId = addNewAddress(scanner, connection);

		// Parse the date of birth string into a java.sql.Date
		Date dob = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed = format.parse(dobStr);
			dob = new Date(parsed.getTime());
		} catch (ParseException e) {
			System.out.println("Error parsing date of birth.");
			e.printStackTrace();
		}
		String sqlEmp = "INSERT INTO employee (first_name, last_name, phone, email, date_of_birth, address_id) VALUES (?, ?, ?, ?, ?, ?)";
		int employeeId = -1;
		try (PreparedStatement pstmt = connection.prepareStatement(sqlEmp, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, phoneNum);
			pstmt.setString(4, email);
			pstmt.setDate(5, dob);
			pstmt.setInt(6, addressId);
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					employeeId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating employee failed, no ID obtained.");
				}
				System.out.println("Employee added successfully!");
			} catch (SQLException e) {
				System.out.println("Error adding employee.");
				e.printStackTrace();
			}
			return employeeId;
		}
	}

	public static int addNewMedicine(Scanner scanner, Connection connection) {
		scanner.nextLine();
		System.out.println("Enter the name of the medicine:");
		String name = scanner.nextLine();

		System.out.println("Enter the description of the medicine:");
		String description = scanner.nextLine();

		String insertQuery = "INSERT INTO medicine (name, description) VALUES (?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating medicine failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int medicineId = generatedKeys.getInt(1);
					System.out.println("Medicine created successfully with ID: " + medicineId);
					return medicineId;
				} else {
					throw new SQLException("Creating medicine failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while creating the medicine.");
			return -1;
		}
	}

	public static int addNewPatient(Scanner scanner, Connection connection) throws SQLException {
		scanner.nextLine();
		int addressId = addNewAddress(scanner, connection);

		scanner.nextLine();
		System.out.println("Enter the first name of the patient:");
		String firstName = scanner.nextLine();

		System.out.println("Enter the last name of the patient:");
		String lastName = scanner.nextLine();
		String dob, phone, email;

		do {
			System.out.print("Enter date of birth (YYYY-MM-DD): ");
			dob = scanner.nextLine();
			if (!isValidDate(dob)) {
				System.out.println("Invalid date of birth");
			}
		} while (!isValidDate(dob));
		do {
			System.out.print("Enter phone number: ");
			phone = scanner.nextLine();
			if (!isValidPhoneNumber(phone)) {
				System.out.println("Invalid phone number");
			}
		} while (!isValidPhoneNumber(phone));
		do {
			System.out.print("Enter email: ");
			email = scanner.nextLine();
			if (!isValidEmail(email)) {
				System.out.println("Invalid email");
			}
		} while (!isValidEmail(email));

		String insertQuery = "INSERT INTO patient (address_id, first_name, last_name, phone, email, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, addressId);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
			pstmt.setDate(6, Date.valueOf(dob));

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating patient failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int patientId = generatedKeys.getInt(1);
					System.out.println("Patient created successfully with ID: " + patientId);
					return patientId;
				} else {
					throw new SQLException("Creating patient failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while creating the patient.");
			return -1;
		}
	}

	public static int addNewPharmacist(Scanner scanner, Connection connection) throws SQLException {
		int employeeId = addNewEmployee(scanner, connection);

		String insertQuery = "INSERT INTO pharmacist (employee_id) VALUES (?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, employeeId);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating pharmacist failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int pharmacistId = generatedKeys.getInt(1);
					System.out.println("Pharmacist created successfully with ID: " + pharmacistId);
					return pharmacistId;
				} else {
					throw new SQLException("Creating pharmacist failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while creating the pharmacist.");
			return -1;
		}
	}

	public static void addNewPharmacyInventoryItem(Scanner scanner, Connection connection) {
		scanner.nextLine();
		String medicinePrompt = "";
		while (true) {
			System.out.println("Is the item a medicine? (y/n):");
			medicinePrompt = scanner.nextLine().trim().toLowerCase();
			if (medicinePrompt.equals("y") || medicinePrompt.equals("n")) {
				break;
			} else {
				System.out.println("Invalid input. Please enter 'y' or 'n'.");
			}
		}

		Integer medicineId = null;
		String itemName = "";
		if (medicinePrompt.equals("y")) {
			medicineId = addNewMedicine(scanner, connection);
			// Fetch the name of the medicine
			String fetchNameQuery = "SELECT name FROM medicine WHERE medicine_id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(fetchNameQuery)) {
				pstmt.setInt(1, medicineId);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					itemName = rs.getString("name");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error while fetching the medicine name.");
				return;
			}
		} else {
			System.out.println("Enter the item name:");
			itemName = scanner.nextLine();
		}

		System.out.println("Enter the stock amount:");
		int stockAmount = scanner.nextInt();

		System.out.println("Enter the price:");
		double price = scanner.nextDouble();
		scanner.nextLine(); // Consume the newline character

		String insertQuery = "INSERT INTO pharmacy_inventory (medicine_id, item_name, stock_amount, price) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			if (medicineId != null) {
				pstmt.setInt(1, medicineId);
			} else {
				pstmt.setNull(1, java.sql.Types.INTEGER);
			}
			pstmt.setString(2, itemName);
			pstmt.setInt(3, stockAmount);
			pstmt.setDouble(4, price);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating pharmacy inventory item failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int inventoryId = generatedKeys.getInt(1);
					System.out.println("Pharmacy inventory item created successfully with ID: " + inventoryId);
				} else {
					throw new SQLException("Creating pharmacy inventory item failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while creating the pharmacy inventory item.");
		}
	}

	private static void addNewPrescription(Scanner scanner, Connection connection) throws SQLException {
		scanner.nextLine();
		try {
			System.out.println("Enter patient ID:");
			int patientId = scanner.nextInt();

			System.out.println("Enter medicine ID:");
			int medicineId = scanner.nextInt();

			System.out.println("Enter pharmacist ID:");
			int pharmacistId = scanner.nextInt();

			System.out.println("Enter dosage in milligrams:");
			int dosage = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

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

	public static void deleteAddress(Connection connection, int addressId) {
	    String sql = "DELETE FROM address WHERE address_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, addressId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Address deleted successfully.");
	}

	public static void deleteAppointment(Connection connection, int appointmentId) {
	    String sql = "DELETE FROM appointment WHERE appointment_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, appointmentId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Appointment deleted successfully.");
	}

	public static void deleteDepartment(Connection connection, int departmentId) {
	    String sql = "DELETE FROM department WHERE department_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, departmentId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Department deleted successfully.");

	}

	public static void deleteDoctor(Connection connection, int doctorId) {
	    String sql = "DELETE FROM doctor WHERE doctor_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, doctorId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Doctor deleted successfully.");

	}

	public static void deleteEmployee(Connection connection, int employeeId) {
	    String sql = "DELETE FROM employee WHERE employee_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, employeeId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Employee deleted successfully.");

	}

	public static void deleteMedicine(Connection connection, int medicineId) {
	    String sql = "DELETE FROM medicine WHERE medicine_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, medicineId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Medicine deleted successfully.");

	}

	public static void deletePatient(Connection connection, int patientId) {
	    String sql = "DELETE FROM patient WHERE patient_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, patientId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Patient deleted successfully.");

	}

	public static void deletePharmacist(Connection connection, int pharmacistId) {
	    String sql = "DELETE FROM pharmacist WHERE pharmacist_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, pharmacistId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Pharmacist deleted successfully.");

	}

	public static void deletePharmacyInventory(Connection connection, int inventoryId) {
	    String sql = "DELETE FROM pharmacy_inventory WHERE inventory_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, inventoryId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Pharmacy inventory item deleted successfully.");
	}

	public static void deletePrescription(Connection connection, int prescriptionId) {
	    String sql = "DELETE FROM prescription WHERE prescription_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, prescriptionId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("Prescription deleted successfully.");

	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		String phoneNumberRegex = "^05\\d{8}$|^05\\d-\\d{7}$";

		Pattern pattern = Pattern.compile(phoneNumberRegex);

		if (phoneNumber == null) {
			return false;
		}

		Matcher matcher = pattern.matcher(phoneNumber);

		return matcher.matches();
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pattern = Pattern.compile(emailRegex);

		if (email == null) {
			return false;
		}

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public static boolean isValidDate(String date) {
		String dateRegex = "^(\\d{4})-(\\d{2})-(\\d{2})$";

		Pattern pattern = Pattern.compile(dateRegex);

		if (date == null) {
			return false;
		}

		Matcher matcher = pattern.matcher(date);

		if (!matcher.matches()) {
			return false;
		}

		int year = Integer.parseInt(matcher.group(1));
		int month = Integer.parseInt(matcher.group(2));
		int day = Integer.parseInt(matcher.group(3));

		try {
			LocalDate parsedDate = LocalDate.of(year, month, day);
			// Additional check to ensure the parsed date matches the input date string
			return parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(date);
		} catch (DateTimeParseException | IllegalArgumentException e) {
			return false;
		}
	}
}



