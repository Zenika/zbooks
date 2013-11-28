package com.zenika.zbooks.gwt.services;

import java.util.List;

import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;

public interface ZBookService {

	ZBook createOrUpdate(long isbn, ZenikaCollection collection);
	ZBook createOrUpdate(ZBook zBook);
	void delete(long isbn);
	void deleteAll();
	List<ZBook> findAll();
	ZBook findByIsbn(long isbn);
}
