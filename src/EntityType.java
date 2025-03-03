public enum EntityType {
    ADDRESS(1, "Address"),
    APPOINTMENT(2, "Appointment"),
    DEPARTMENT(3, "Department"),
    DOCTOR(4, "Doctor"),
    EMPLOYEE(5, "Employee"),
    MEDICINE(6, "Medicine"),
    PATIENT(7, "Patient"),
    PHARMACIST(8, "Pharmacist"),
    PHARMACY_INVENTORY(9, "Pharmacy Inventory"),
    PRESCRIPTION(10, "Prescription");

    private final int id;
    private final String name;

    EntityType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static EntityType fromId(int id) {
        for (EntityType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ID: " + id);
    }

    @Override
    public String toString() {
        return name;
    }
}