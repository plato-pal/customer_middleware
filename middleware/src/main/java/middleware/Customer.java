package middleware;


public class Customer {
	
	
	private String name;
	private String policyNumber;
	private String email;
	
	public Customer() {}

	public Customer(String name, String policyNumber, String email) {
		super();
		this.name = name;
		this.policyNumber = policyNumber;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
