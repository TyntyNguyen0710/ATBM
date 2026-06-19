package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DataBase.JDBCUltil;
import model.User;

public class userDAO implements DAOInterface<User> {

	public static userDAO getIntance() {
		return new userDAO();
	}

	@Override
	public int insert(User user) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "INSERT INTO Users (username, password) VALUES (?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getPassword());

				// Execute the insert
				result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	@Override
	public int update(User user) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "UPDATE Users SET password=? WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, user.getPassword());
				preparedStatement.setString(2, user.getUsername());

				// Execute the update
				result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	@Override
	public int delete(User user) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "DELETE FROM Users WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, user.getUsername());

				// Execute the delete
				result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	public int deleteByUsername(String username) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "DELETE FROM Users WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);

				// Execute the delete
				result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}

	@Override
	public ArrayList<User> selectAll() throws ClassNotFoundException {
		ArrayList<User> userList = new ArrayList<>();
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "SELECT * FROM Users";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
					ResultSet rs = preparedStatement.executeQuery()) {

				while (rs.next()) {
					String username = rs.getString("username");
					String password = rs.getString("password");

					User user = new User(username, password);
					userList.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return userList;
	}

	@Override
	public User selectByID(String username) throws ClassNotFoundException {
		User user = null;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "SELECT * FROM Users WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);

				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						String fetchedUsername = rs.getString("username");
						String password = rs.getString("password");

						user = new User(fetchedUsername, password);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return user;
	}


	public int updatePassword(String username, String password) throws ClassNotFoundException {
		int result = 0;
		Connection connection = JDBCUltil.getConnection();

		try {
			String sql = "UPDATE Users SET password=? WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, password);
				preparedStatement.setString(2, username);

				// Execute the update
				result = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception based on your application's needs
		} finally {
			JDBCUltil.closeConnection(connection);
		}

		return result;
	}
}
