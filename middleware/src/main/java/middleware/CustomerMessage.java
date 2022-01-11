package middleware;

import java.io.Serializable;

public class CustomerMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8261048088860395263L;
	
	private Customer customer;
	
	public CustomerMessage(Customer customer) {
		super();
		this.customer = customer;
	}
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
