public class Department {
    private int departmentID;
    private String name;
    private int floor;
    private Doctor manager;
    private String contactNumber;
    
	public Department(String name, int floor, Doctor manager, String contactNumber) {
		super();
		this.name = name;
		this.floor = floor;
		this.manager = manager;
		this.contactNumber = contactNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public Doctor getManager() {
		return manager;
	}
	public void setManager(Doctor manager) {
		this.manager = manager;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public int getDepartmentID() {
		return departmentID;
	}

}