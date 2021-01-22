package ua.lviv.lgs.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.utils.ConnectionUtils;

public class BucketDaoImpl implements BucketDao {

	private static String READ_ALL = "select * from bucket";
	private static String CREATE = "insert into bucket(`user_id`,`product_id`, `purchase_date`) values (?,?,?)";
	private static String READ_BY_ID = "select * from bucket where id = ?";
	private static String DELETE_BY_ID = "delete from bucket where id = ?";

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
	public BucketDaoImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		conn = ConnectionUtils.openConnection();
	}

	@Override
	public Bucket create(Bucket bucket) throws SQLException {
		preparedStatement = conn.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, bucket.getUserId());
		preparedStatement.setInt(2, bucket.getProductId());
		preparedStatement.setDate(3, new Date(bucket.getPurchaseDate().getTime()));
		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		rs.next();
		bucket.setId(rs.getInt(1));
		return bucket;
	}

	@Override
	public Bucket read(int id) throws SQLException {
		Bucket bucket = null;
		preparedStatement = conn.prepareStatement(READ_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();
		result.next();
		bucket = new Bucket(result.getInt("id"), result.getInt("user_id"), result.getInt("product_id"),
				result.getDate("purchase_date"));
		return bucket;
	}

	@Override
	public Bucket update(Bucket t) {
		throw new IllegalStateException();
	}

	@Override
	public void delete(int id) throws SQLException {
		preparedStatement = conn.prepareStatement(DELETE_BY_ID);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

	}

	@Override
	public List<Bucket> readAll() throws SQLException {
		List<Bucket> list = new ArrayList<>();
		preparedStatement = conn.prepareStatement(READ_ALL);
		ResultSet result = preparedStatement.executeQuery();
		while (result.next()) {
			list.add(new Bucket(result.getInt("id"), result.getInt("user_id"), result.getInt("product_id"),
					result.getDate("purchase_date")));
		}
		return list;
	}

}
