package Controller;

import dao.customerDAO;
//import java.util.ArrayList;
//import dao.bookingDAO;
//import model.Booking;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
//		ArrayList<Booking> bookings = bookingDAO.getIntance().selectAll();
//		for (Booking booking : bookings) {
//			System.out.println(booking);
//		}
		System.out.println(customerDAO.getIntance().selectByEmail("philong2m@gmail.com"));
	}
}
