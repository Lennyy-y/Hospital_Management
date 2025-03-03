public class Doctor extends Employee {
    private static int doctorIdCounter = 1;
    private String specialty;

    public Doctor(String firstName, String lastName, String dob, String specialty) {
        super(firstName, lastName, dob);
        this.id = doctorIdCounter++;
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return super.toString() + ", Specialty: " + specialty;
    }
}
