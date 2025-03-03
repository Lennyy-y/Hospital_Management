public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String medicalHistory;

    public Patient(int id, String firstName, String lastName, String medicalHistory) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + firstName + " " + lastName + ", Medical History: " + medicalHistory;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
}
