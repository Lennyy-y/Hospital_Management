public class Pharmacist extends Employee {
    private static int pharmacistIdCounter = 1;
    private String qualification;

    public Pharmacist(String firstName, String lastName, String dob, String qualification) {
        super(firstName, lastName, dob);
        this.id = pharmacistIdCounter++;
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    @Override
    public String toString() {
        return super.toString() + ", Qualification: " + qualification;
    }
}
