import java.util.Scanner;

public class Pharmacy {
    private int pharmacyID;
    private Address address;
    private String openingHours;
    private Pharmacist pharmacist;
    private String contactNumber;
    private String email;

    public Pharmacy(int pharmacyID, Address address, String openingHours, Pharmacist pharmacist, String contactNumber, String email) {
        this.pharmacyID = pharmacyID;
        this.address = address;
        this.openingHours = openingHours;
        this.pharmacist = pharmacist;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public Address getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void displayPharmacyInfo() {
        System.out.println("Pharmacy ID: " + pharmacyID);
        address.displayAddress();
        System.out.println("Opening Hours: " + openingHours);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email: " + email);
    }
}

