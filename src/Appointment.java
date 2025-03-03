import java.sql.Date;

public class Appointment {
	private int appointmentID;
	private Doctor doctor;
	private Patient patient;
	private Date appointmentDate;
	private Date appointmentTime;
	private String reason;

	public Appointment(Doctor doctor, Patient patient, Date appointmentDate, Date appointmentTime, String reason) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.reason = reason;
	}

	public int getAppointmentID() {
		return appointmentID;
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

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}