package com.zenika.zbooks.gwt.dao;

import java.util.List;

import com.zenika.zbooks.gwt.client.entity.Author;

public interface AuthorRepository {

	List<Author> findAll();
	
	Author findByName(String firstName, String lastName);
	
	Author findById(int id);
	
	void save (Author author);
	
	void delete (Author author);
}
