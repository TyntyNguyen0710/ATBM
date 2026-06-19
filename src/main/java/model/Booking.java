package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
	public long id;
	private Customer customer;
	private Date departureDate;
	private int noAdults;
	private int noChildren;
	private String email;
	private int tourID;
	private int customerID;

	public Booking(Customer customer, Date departureDate, int noAdults, int noChildren, String email, int tourID,
			int customerID) {
		super();
		this.customer = customer;
		this.departureDate = departureDate;
		this.noAdults = noAdults;
		this.noChildren = noChildren;
		this.email = customer.getEmail();
		this.tourID = tourID;
		this.customerID = customerID;
	}

	public Booking(Customer customer, Date departureDate, int noAdults, int noChildren, String email, int tourID) {
		super();
		this.customer = customer;
		this.departureDate = departureDate;
		this.noAdults = noAdults;
		this.noChildren = noChildren;
		this.email = email;
		this.tourID = tourID;
	}

	
	public Booking(Date departureDate, int noAdults, int noChildren, String email, int tourID, int customerID) {
		super();
		this.departureDate = departureDate;
		this.noAdults = noAdults;
		this.noChildren = noChildren;
		this.email = email;
		this.tourID = tourID;
		this.customerID = customerID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public int getNoAdults() {
		return noAdults;
	}

	public void setNoAdults(int noAdults) {
		this.noAdults = noAdults;
	}

	public int getNoChildren() {
		return noChildren;
	}

	public void setNoChildren(int noChildren) {
		this.noChildren = noChildren;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", customer=" + customer + ", departureDate=" + departureDate + ", noAdults="
				+ noAdults + ", noChildren=" + noChildren + ", email=" + email + ", tourID=" + tourID + ", customerID="
				+ customerID + "]";
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String dinhDangNgay() {
		SimpleDateFormat dinhdang = new SimpleDateFormat("dd/mm/yyyy");
		return dinhdang.format(departureDate);

	}
}
