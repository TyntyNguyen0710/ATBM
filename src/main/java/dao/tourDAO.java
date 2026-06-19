package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataBase.JDBCUltil;
import model.Tour;

public class tourDAO implements DAOInterface<Tour> {
	public static tourDAO getIntance() {
		return new tourDAO();
	}

	public int insert(Tour t) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		int result = 0;
		Connection con = JDBCUltil.getConnection();
		String sql = "insert into Tour(tourId,tenTour,duration,schedule,departure,price,transport)"
				+ "values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, t.getId());
			pst.setString(2, t.getName());
			pst.setString(3, t.getDuration());
			pst.setString(4, t.getSchedule());
			pst.setString(5, t.getDeparture());
			pst.setFloat(6, t.getPrice());
			pst.setString(7, t.getTransport());
			result = pst.executeUpdate();
			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int update(Tour t) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		int result = 0;
		Connection con = JDBCUltil.getConnection();
		String sql = "UPDATE Tour " + "SET tenTour=?, duration=?, schedule=?, departure=?, price=?, transport=? "
				+ "WHERE tourId=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, t.getName());
			pst.setString(2, t.getDuration());
			pst.setString(3, t.getSchedule());
			pst.setString(4, t.getDeparture());
			pst.setFloat(5, t.getPrice());
			pst.setString(6, t.getTransport());
			pst.setInt(7, t.getId());
			result = pst.executeUpdate();
			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public int delete(Tour t) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		int result = 0;
		Connection con = JDBCUltil.getConnection();
		String sql = "DELETE FROM Tour" + " WHERE tourId=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, t.getId());
			result = pst.executeUpdate();
			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public Tour selectByID(String tourId) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Tour result = null;
		Connection con = JDBCUltil.getConnection();
		String sql = "SELECT * FROM Tour WHERE tourId=?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, tourId); // Set the tourId as a String parameter
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("tourId");
				String name = rs.getString("tenTour");
				String duration = rs.getString("duration");
				String schedule = rs.getString("schedule");
				String departure = rs.getString("departure");
				String transport = rs.getString("transport");
				result = new Tour(id, name, duration, schedule, departure, id, transport);
			}

			JDBCUltil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList<Tour> selectAll() throws SQLException, ClassNotFoundException {
		ArrayList<Tour> result = new ArrayList<Tour>();
		Connection con = JDBCUltil.getConnection();

		String sql = "SELECT t.tourId, t.tenTour, t.duration, t.schedule, t.departure, t.price, t.transport, i.img1, i.img2, i.img3, i.img4, i.img5, i.img6 "
				+ "FROM Tour t LEFT JOIN imgTour i ON t.tourId = i.tourId";
		if (con != null) {
			PreparedStatement ps = con.prepareStatement(sql);
			try {
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("tourId");
					String name = rs.getString("tenTour");
					String duration = rs.getString("duration");
					String schedule = rs.getString("schedule");
					String departure = rs.getString("departure");
					float price = rs.getFloat("price");
					String transport = rs.getString("transport");

					ArrayList<String> imagePaths = new ArrayList<String>();
					imagePaths.add(rs.getString("img1"));
					imagePaths.add(rs.getString("img2"));
					imagePaths.add(rs.getString("img3"));
					imagePaths.add(rs.getString("img4"));
					imagePaths.add(rs.getString("img5"));
					imagePaths.add(rs.getString("img6"));

					Tour tour = new Tour(id, name, duration, schedule, departure, price, transport, imagePaths);
					result.add(tour);

				}
				JDBCUltil.closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Kết nối thất bại, vui lòng kiểm tra lại JDBCUltil!");
		}
		return result;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		ArrayList<Tour> t = tourDAO.getIntance().selectAll();
		for (String s : t.get(0).getImagePaths()) {
			System.out.println(s);
		}
		Tour t1 = tourDAO.getIntance().selectByID("1");
		System.out.println(t1.getName());
	}
}
