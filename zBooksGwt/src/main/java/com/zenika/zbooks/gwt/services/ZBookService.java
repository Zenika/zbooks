package com.zenika.zbooks.gwt.services;

import java.util.List;

import com.zenika.zbooks.gwt.client.entity.ZBook;

public interface ZBookService {

	ZBook create(ZBook zBook);
	void delete(int isbn);
	List<ZBook> findAll();
	ZBook update(ZBook zBook);
	ZBook findByIsbn(int isbn);
}
