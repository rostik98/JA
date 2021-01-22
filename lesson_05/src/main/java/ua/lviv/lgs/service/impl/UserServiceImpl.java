package ua.lviv.lgs.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.dao.impl.UserDaoImpl;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public UserServiceImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		userDao = new UserDaoImpl();
	}

	@Override
	public User create(User t) throws SQLException {
		return userDao.create(t);
	}

	@Override
	public User read(int id) throws SQLException {
		return userDao.read(id);
	}

	@Override
	public User update(User t) throws SQLException {
		return userDao.update(t);
	}

	@Override
	public void delete(int id) throws SQLException {
		userDao.delete(id);
	}

	@Override
	public List<User> readAll() throws SQLException {
		return userDao.readAll();
	}

}
