package com.zenika.zbooks.gwt.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.dao.AuthorRepository;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Author> findAll() {
		return this.em.createQuery("SELECT author FROM Author author ORDER BY ID", Author.class).getResultList();
	}

	@Override
	public Author findById(int id) {
		return this.em.find(Author.class, id);
	}
	
	@Override
	public Author findByName (String firstName, String lastName) {
		TypedQuery<Author> query = this.em.createQuery("SELECT author FROM Author author WHERE firstName=:firstName AND lastName=:lastName", Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getSingleResult();
	}

	@Override
	public void save(Author author) {
		this.em.persist(author);
	}

	@Override
	public void delete(Author author) {
		this.em.remove(author);
	}

}
