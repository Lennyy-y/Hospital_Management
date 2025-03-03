import java.util.Scanner;

public class Address {
    private int addressID;
    private String city;
    private String street;
    private String postalCode;

    public Address(int addressID, String city, String street, String number, String postalCode) {
        this.addressID = addressID;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }


    public String getPostalCode() {
        return postalCode;
    }

    public void displayAddress() {
        System.out.println("Address: " + street + ", " + city + ", " + postalCode);
    }

    public static Address getAddressFromUser(Scanner scanner) {
        System.out.println("Enter Address ID: ");
        int addressID = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter City: ");
        String city = scanner.nextLine();

        System.out.println("Enter Street: ");
        String street = scanner.nextLine();

        System.out.println("Enter Number: ");
        String number = scanner.nextLine();

        System.out.println("Enter Postal Code: ");
        String postalCode = scanner.nextLine();

        return new Address(addressID, city, street, number, postalCode);
    }
}
