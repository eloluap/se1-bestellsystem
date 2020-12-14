package datamodel;

public class Customer {
	private final String id;
	private String firstName;
	private String lastName;
	private String contact;
	
	protected Customer(String id, String name, String contact) {
		this.id = id;
		this.firstName = "";
		this.lastName = name;
		this.contact = contact;
		if (name == null) {
			this.lastName = "";
		}
		if (contact == null) {
			this.contact = "";
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		if (firstName == null) {
			this.firstName = "";
		}
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
		if (lastName == null) {
			this.lastName = "";
		}
	}
	
	public String getContact() {
		return this.contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
		if (contact == null) {
			this.contact = "";
		}
	}
}