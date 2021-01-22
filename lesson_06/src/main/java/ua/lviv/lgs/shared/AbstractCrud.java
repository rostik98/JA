package ua.lviv.lgs.shared;

import java.util.List;

public interface AbstractCrud<T> {

	T create(T t);

	T read(int id);

	T update(T t);

	void delete(int id);

	List<T> readAll();

}
