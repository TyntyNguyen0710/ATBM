package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUltil {
	public static Connection getConnection() throws ClassNotFoundException {
		Connection con = null;
		try {
			String URL = "jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=ProjectWeb;encrypt=false;trustServerCertificate=true;characterEncoding=UTF-8";
			String USER = "sa";
			String PASSWORD = "123@";

			// Lấy Driver của SQL Server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("Lỗi: Thiếu thư viện JDBC Driver (sqljdbc4.jar hoặc mssql-jdbc.jar)");
		} catch (SQLException e) {
			System.err.println("Lỗi kết nối: Có thể chưa bật TCP/IP cổng 1433 hoặc sai Pass sa");
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection c) {
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		Connection c = JDBCUltil.getConnection();
		System.out.println(c);
	}
}
