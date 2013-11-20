package com.zenika.zbooks.gwt.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.dao.ZBookRepository;

@Repository
public class ZBookRepositoryImpl implements ZBookRepository {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ZBook> findAll() {
		return this.em.createQuery("SELECT zBook FROM ZBook zBook ORDER BY isbn").getResultList();
	}

	@Override
	public ZBook findByIsbn(int isbn) {
		return this.em.find(ZBook.class, isbn);
	}

	@Override
	public ZBook save(ZBook zBook) {
		return this.em.merge(zBook);
	}

	@Override
	public void delete(ZBook zBook) {
		this.em.remove(zBook);
	}
}
