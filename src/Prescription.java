import java.sql.Date;

public class Prescription {
	private int prescriptionID;
	private Doctor doctor;
	private Patient patient;
	private Medicine medicine;
	private String dosage;
	private Date startDate;
	private Date endDate;

	public Prescription(Doctor doctor, Patient patient, Medicine medicine, String dosage, Date startDate,
			Date endDate) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.medicine = medicine;
		this.dosage = dosage;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getPrescriptionID() {
		return prescriptionID;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}