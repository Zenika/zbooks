package com.zenika.zbooks.gwt.dao;

import java.util.List;

import com.zenika.zbooks.gwt.client.entity.ZBook;


public interface ZBookRepository{

	List<ZBook> findAll();
	
	ZBook findByIsbn(long isbn);
	
	ZBook save (ZBook zBook);
	
	void delete (ZBook zBook);
}
