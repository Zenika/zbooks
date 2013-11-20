package com.zenika.zbooks.gwt.services;

import java.util.List;

import com.zenika.zbooks.gwt.client.entity.ZBook;

public interface ZBookService {

	ZBook createOrUpdate(ZBook zBook);
	void delete(int isbn);
	void deleteAll();
	List<ZBook> findAll();
	ZBook findByIsbn(int isbn);
}
