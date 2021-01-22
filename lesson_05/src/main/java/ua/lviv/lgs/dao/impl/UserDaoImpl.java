package ua.lviv.lgs.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.utils.ConnectionUtils;

public class UserDaoImpl implements UserDao {

	private static String READ_ALL = "select * from user";
	private static String CREATE = "insert into user(`email`, `first_name`, `last_name`, `role`) values (?,?,?,?)";
	private static String READ_BY_ID = "select * from user where id = ?";
	private static String UPDATE_BY_ID = "update user set email = ?, role = ? where id = ?";
	private static String DELETE_BY_ID = "delete from product where id = ?";

	private Connection conn;
	private PreparedStatement preparedStatement;

	/**
	 * @param conn
	 * @throws SQLException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public UserDaoImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		conn = ConnectionUtils.openConnection();
	}

	@Override
	public User create(User user) throws SQLException {
		preparedStatement = conn.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, user.getEmail());
		preparedStatement.setString(2, user.getFirstName());
		preparedStatement.setString(3, user.getLastName());
		preparedStatement.setString(4, user.getRole());
		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		rs.next();
		user.setId(rs.getInt(1));
		return user;
	}

	@Override
	public User read(int id) throws SQLException {
		User user = null;
		preparedStatement = conn.prepareStatement(READ_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();
		result.next();
		user = new User(result.getString("email"), result.getString("first_name"), result.getString("last_name"),
				result.getString("role"));
		return user;
	}

	@Override
	public User update(User user) throws SQLException {
		preparedStatement = conn.prepareStatement(UPDATE_BY_ID);
		preparedStatement.setString(1, user.getEmail());
		preparedStatement.setString(2, user.getRole());
		preparedStatement.setInt(3, user.getId());
		preparedStatement.executeUpdate();
		return user;
	}

	@Override
	public void delete(int id) throws SQLException {
		preparedStatement = conn.prepareStatement(DELETE_BY_ID);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}

	@Override
	public List<User> readAll() throws SQLException {
		List<User> list = new ArrayList<>();
		preparedStatement = conn.prepareStatement(READ_ALL);
		ResultSet result = preparedStatement.executeQuery();
		while (result.next()) {
			list.add(new User(result.getString("email"), result.getString("first_name"), result.getString("last_name"),
					result.getString("role")));
		}
		return list;
	}

}
