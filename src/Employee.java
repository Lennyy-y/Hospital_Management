public class Employee {
    private static int employeeIdCounter = 1;

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String dob;

    public Employee(String firstName, String lastName, String dob) {
        this.id = employeeIdCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + firstName + " " + lastName + ", DOB: " + dob;
    }
}
