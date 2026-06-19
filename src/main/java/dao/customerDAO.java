package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DataBase.JDBCUltil;
import model.Customer;

public class customerDAO implements DAOInterface<Customer> {
	public static customerDAO getIntance() {
		return new customerDAO();
	}

	public int insert(Customer customer) throws ClassNotFoundException {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JDBCUltil.getConnection();

			// Check if the username exists in the Users table
			if (usernameExists(connection, customer.getUser().getUsername())) {
				String sql = "INSERT INTO Customer (name, address, email, phone, username,role) VALUES (?, ?, ?, ?, ?, ?)";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, customer.getName());
				preparedStatement.setString(2, customer.getAddress());
				preparedStatement.setString(3, customer.getEmail());
				preparedStatement.setString(4, customer.getPhone());
				preparedStatement.setString(5, customer.getUser().getUsername());
				preparedStatement.setString(6, "Customer");

				// Execute the insert
				result = preparedStatement.executeUpdate();
			} else {
				// Handle the case where the username does not exist
				System.err.println("Error: Username does not exist in the Users table.");
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closePreparedStatement(preparedStatement);
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	private boolean usernameExists(Connection connection, String username) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next() && resultSet.getInt(1) > 0;
			}
		}
	}

	public int update(Customer customer) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		String sql = "UPDATE Customer SET name=?, address=?, phone=? WHERE email=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getAddress());
			preparedStatement.setString(3, customer.getPhone());
			preparedStatement.setString(4, customer.getEmail());

			// Execute the update
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	public int delete(Customer customer) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		String sql = "DELETE FROM Customer WHERE email=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, customer.getEmail());

			// Execute the delete
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	public ArrayList<Customer> selectAll() throws ClassNotFoundException {
		ArrayList<Customer> result = new ArrayList<>();
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Customer WHERE role <> 'Admin' ";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phone = rs.getString("phone");

				// Tạo đối tượng Customer với thông tin từ ResultSet
				Customer customer = new Customer(name, address, email, phone);
				result.add(customer);
			}
			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int deleteByEmail(String customerEmail) throws ClassNotFoundException {
		Connection con = JDBCUltil.getConnection();
		String sql = "DELETE FROM Customer WHERE email=?";
		int result = 0;

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setNString(1, customerEmail);

			// Execute the delete statement
			result = pst.executeUpdate();
			userDAO.getIntance().deleteByUsername(selectByEmail(customerEmail).getUser().getUsername());
			// Close the PreparedStatement and Connection
			pst.close();

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			// Handle any SQL errors
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Customer selectByID(String customerID) throws ClassNotFoundException {
		Customer result = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Customer WHERE id=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, customerID);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				// Create a Customer object with information from ResultSet
				result = new Customer(id, name, address, email, phone);
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Customer selectByIDCustomer(int customerId) throws ClassNotFoundException {
		Customer result = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Customer WHERE id=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, customerId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				// Create a Customer object with information from ResultSet
				result = new Customer(id, name, address, email, phone);
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Customer selectByUsername(String username) throws ClassNotFoundException {
		Customer result = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Customer WHERE username=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				// Create a Customer object with information from ResultSet
				result = new Customer(id, name, address, email, phone);
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Customer selectByEmail(String emailCus) throws ClassNotFoundException {
		Customer result = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Customer WHERE email=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, emailCus);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				// Create a Customer object with information from ResultSet
				result = new Customer(id, name, address, email, phone);
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getUserRole(String username) throws ClassNotFoundException {
		try (Connection connection = JDBCUltil.getConnection()) {
			String sql = "SELECT role FROM Customer WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getString("role");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Return null if role is not found
	}

	public String selectEmailByUsername(String username) throws ClassNotFoundException {
		String email = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT email FROM Customer WHERE username=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();

			// Kiểm tra xem ResultSet có dữ liệu không
			if (rs.next()) {
				email = rs.getString("email");
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return email;
	}

	public int insertNoLogin(Customer customer) throws ClassNotFoundException {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JDBCUltil.getConnection();

			// Check if the username exists in the Users table

			String sql = "INSERT INTO Customer (name, address, email, phone, role) VALUES (?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getAddress());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getPhone());
			preparedStatement.setString(5, "Customer");

			// Execute the insert
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closePreparedStatement(preparedStatement);
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	public int update(String name, String phone, String address, String username) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		String sql = "UPDATE Customer SET name=?, phone=?, address=? WHERE username=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phone);
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, username);

			// Execute the update
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}
}
