import java.sql.Date;

public class Medicine {
	private int medicineID;
	private Date expirationDate;
	private String type;
	private double price;
	private int doseMG;

	public Medicine(Date expirationDate, String type, double price, int doseMG, String manufacturer,
			String batchNumber) {
		super();
		this.expirationDate = expirationDate;
		this.type = type;
		this.price = price;
		this.doseMG = doseMG;
		this.manufacturer = manufacturer;
		this.batchNumber = batchNumber;
	}

	public int getMedicineID() {
		return medicineID;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDoseMG() {
		return doseMG;
	}

	public void setDoseMG(int doseMG) {
		this.doseMG = doseMG;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	private String manufacturer;
	private String batchNumber;

}