package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DataBase.JDBCUltil;

public class Customer {
	private int ID;
	private String name;
	private String address;
	private String email;
	private String phone;
	private User user;
	public Customer() {
	}

	public Customer(String name, String address, String email, String phone) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;

	}
	

	public Customer(String name, String address, String email, String phone, User user) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.user = user;
	}

	public Customer(int iD, String name, String address, String email, String phone) {
		super();
		ID = iD;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer [ID=" + ID + ", name=" + name + ", address=" + address + ", email=" + email + ", phone="
				+ phone + ", user=" + user + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIDByUsername(String username) throws ClassNotFoundException {
	    int userID = -1; // Default value indicating no user found

	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        // Assuming you have a Connection object named 'connection'
	        connection = JDBCUltil.getConnection();

	        // Define the SQL query for selecting the ID based on username
	        String sql = "SELECT id FROM Users WHERE username = ?";

	        // Create a PreparedStatement with the SQL query
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, username);

	        // Execute the query
	        resultSet = preparedStatement.executeQuery();

	        // Check if a user was found
	        if (resultSet.next()) {
	            userID = resultSet.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle the exception based on your application's needs
	    } finally {
	        // Close resources in the reverse order of their creation
	        JDBCUltil.closePreparedStatement(preparedStatement);
	        JDBCUltil.closeConnection(connection);
	    }

	    return userID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}