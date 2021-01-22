package ua.lviv.lgs.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.ProductDao;
import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.utils.ConnectionUtils;

public class ProductDaoImpl implements ProductDao {

	private static String READ_ALL = "select * from product";
	private static String CREATE = "insert into product(`name`, `description`, `price`) values (?,?,?)";
	private static String READ_BY_ID = "select * from product where id = ?";
	private static String UPDATE_BY_ID = "update product set description = ?, price = ? where id = ?";
	private static String DELETE_BY_ID = "delete from product where id = ?";

	private static Logger Log = Logger.getLogger(ProductDaoImpl.class);

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
	public ProductDaoImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		conn = ConnectionUtils.openConnection();
	}

	@Override
	public Product create(Product product) {

		try {
			preparedStatement = conn.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			product.setId(rs.getInt(1));
		} catch (SQLException e) {
			Log.error(e);
		}
		return product;
	}

	@Override
	public Product read(int id) {
		Product product = null;
		try {
			preparedStatement = conn.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			product = new Product(result.getInt("id"), result.getString("name"), result.getString("description"),
					result.getDouble("price"));
		} catch (SQLException e) {
			Log.error(e);
		}

		return product;
	}

	@Override
	public Product update(Product product) {
		try {
			preparedStatement = conn.prepareStatement(UPDATE_BY_ID);
			preparedStatement.setString(1, product.getDescription());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, product.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Log.error(e);
		}

		return product;
	}

	@Override
	public void delete(int id) {
		try {
			preparedStatement = conn.prepareStatement(DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Log.error(e);
		}

	}

	@Override
	public List<Product> readAll() {
		List<Product> list = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				list.add(new Product(result.getInt("id"), result.getString("name"), result.getString("description"),
						result.getDouble("price")));
			}
		} catch (SQLException e) {
			Log.error(e);
		}

		return list;
	}

}
