package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DataBase.JDBCUltil;
import java.util.List;
import model.Customer;

public class customerDAO implements DAOInterface<Customer> {
	public static customerDAO getIntance() {
		return new customerDAO();
	}
	public String getActivePublicKey(int customerId) {
		String publicKey = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = JDBCUltil.getConnection();
			String sql = "SELECT publicKey FROM UserPublicKeyHistory WHERE customerId = ? AND isActive = 1";
			pst = con.prepareStatement(sql);
			pst.setInt(1, customerId);
			rs = pst.executeQuery();

			if (rs.next()) {
				publicKey = rs.getString("publicKey");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUltil.closeResultSet(rs);
			JDBCUltil.closePreparedStatement(pst);
			JDBCUltil.closeConnection(con);
		}
		return publicKey;
	}

	public List<String> getAllPublicKeysByCustomerId(int customerId) {
		List<String> keys = new ArrayList<>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = JDBCUltil.getConnection();
			String sql = "SELECT publicKey, createdAt, isActive FROM UserPublicKeyHistory WHERE customerId = ? ORDER BY createdAt DESC";
			pst = con.prepareStatement(sql);
			pst.setInt(1, customerId);
			rs = pst.executeQuery();

			while (rs.next()) {
				String key = rs.getString("publicKey");
				keys.add(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUltil.closeResultSet(rs);
			JDBCUltil.closePreparedStatement(pst);
			JDBCUltil.closeConnection(con);
		}
		return keys;
	}

	public int addNewPublicKey(int customerId, String publicKey) {
		int result = 0;
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = JDBCUltil.getConnection();

			String sql1 = "UPDATE UserPublicKeyHistory SET isActive = 0 WHERE customerId = ?";
			pst = con.prepareStatement(sql1);
			pst.setInt(1, customerId);
			pst.executeUpdate();
			pst.close();

			String sql2 = "INSERT INTO UserPublicKeyHistory (customerId, publicKey, isActive) VALUES (?, ?, 1)";
			pst = con.prepareStatement(sql2);
			pst.setInt(1, customerId);
			pst.setString(2, publicKey);
			result = pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUltil.closePreparedStatement(pst);
			JDBCUltil.closeConnection(con);
		}
		return result;
	}
	// Cập nhật Public Key cho khách hàng
	public int updatePublicKey(int customerId, String publicKey) {
		int result = 0;
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = JDBCUltil.getConnection();
			String sql = "UPDATE Customer SET publicKey = ? WHERE id = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, publicKey);
			pst.setInt(2, customerId);
			result = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUltil.closePreparedStatement(pst);
			JDBCUltil.closeConnection(con);
		}
		return result;
	}

	public String getPublicKeyByUsername(String username) {
		String publicKey = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = JDBCUltil.getConnection();
			String sql = "SELECT c.publicKey FROM Customer c JOIN [User] u ON c.userId = u.id WHERE u.username = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			rs = pst.executeQuery();

			if (rs.next()) {
				publicKey = rs.getString("publicKey");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUltil.closeResultSet(rs);
			JDBCUltil.closePreparedStatement(pst);
			JDBCUltil.closeConnection(con);
		}
		return publicKey;
	}
	public int insert(Customer customer) throws ClassNotFoundException {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JDBCUltil.getConnection();

			if (usernameExists(connection, customer.getUser().getUsername())) {
				String sql = "INSERT INTO Customer (name, address, email, phone, username,role) VALUES (?, ?, ?, ?, ?, ?)";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, customer.getName());
				preparedStatement.setString(2, customer.getAddress());
				preparedStatement.setString(3, customer.getEmail());
				preparedStatement.setString(4, customer.getPhone());
				preparedStatement.setString(5, customer.getUser().getUsername());
				preparedStatement.setString(6, "Customer");

				result = preparedStatement.executeUpdate();
			} else {
				System.err.println("Error: Username does not exist in the Users table.");
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
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
