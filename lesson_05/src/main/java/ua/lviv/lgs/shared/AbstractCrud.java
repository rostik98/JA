package ua.lviv.lgs.shared;

import java.sql.SQLException;
import java.util.List;

public interface AbstractCrud<T> {

	T create(T t) throws SQLException;

	T read(int id) throws SQLException;

	T update(T t) throws SQLException;

	void delete(int id) throws SQLException;

	List<T> readAll() throws SQLException;

}
