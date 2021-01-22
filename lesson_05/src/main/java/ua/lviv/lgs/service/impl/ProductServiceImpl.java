package ua.lviv.lgs.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import ua.lviv.lgs.dao.ProductDao;
import ua.lviv.lgs.dao.impl.ProductDaoImpl;
import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;

	public ProductServiceImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		productDao = new ProductDaoImpl();
	}

	@Override
	public Product create(Product t) throws SQLException {
		return productDao.create(t);
	}

	@Override
	public Product update(Product t) throws SQLException {
		return productDao.update(t);
	}

	@Override
	public Product read(int id) throws SQLException {
		return productDao.read(id);
	}

	@Override
	public void delete(int id) throws SQLException {
		productDao.delete(id);
	}

	@Override
	public List<Product> readAll() throws SQLException {
		return productDao.readAll();
	}
}
