package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOInterface<T> {
	public int insert(T t) throws ClassNotFoundException;

	public int update(T t) throws ClassNotFoundException;

	public int delete(T t) throws ClassNotFoundException;

	public ArrayList<T> selectAll() throws ClassNotFoundException, SQLException;

	public T selectByID(String id) throws ClassNotFoundException;

}
