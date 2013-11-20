package com.zenika.zbooks.gwt.services.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.dao.AuthorRepository;
import com.zenika.zbooks.gwt.dao.ZBookRepository;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service
@Transactional
public class ZBookServiceImpl implements ZBookService {

	@Autowired
	private ZBookRepository zBookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public ZBook createOrUpdate(ZBook zBook) {
		List<Author> authors = zBook.getAuthors();
		for (int i=0; i<authors.size(); i++) {
			Author author = authors.get(i);
			try {
				Author authorInDB = authorRepository.findByName(author.getFirstName(), author.getLastName());
				author.setId(authorInDB.getId());
			} catch (NoResultException e) {
				authorRepository.save(author);
			}
		}
		return zBookRepository.save(zBook);
	}

	@Override
	public void delete(int isbn) {
		ZBook zBookToDelete = this.findByIsbn(isbn);
		zBookRepository.delete(zBookToDelete);
	}

	@Override
	public List<ZBook> findAll() {
		return zBookRepository.findAll();
	}

	@Override
	public ZBook findByIsbn(int isbn) {
		return zBookRepository.findByIsbn(isbn);
	}

	@Override
	public void deleteAll() {
		List<ZBook> zBooksList = zBookRepository.findAll();
		for (ZBook zBook : zBooksList) {
			zBookRepository.delete(zBook);
		}
	}

}
