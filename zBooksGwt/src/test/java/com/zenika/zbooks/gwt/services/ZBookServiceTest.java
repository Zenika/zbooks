package com.zenika.zbooks.gwt.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.client.entity.Language;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ZBookServiceTest {

	@Autowired
	private ZBookService zBookService;
	private ZBook zBook1;
	private ZBook zBook2;
	private Author author1;
	private Author author2;
	
	private static final Logger LOG = Logger.getLogger(ZBookServiceTest.class);
	
	
	@Before
	public void setUp() {
		//TODO to change if we add more attributes in the ZBook class
		zBook1 = new ZBook();
		zBook1.setISBN(42);
		zBook1.setTitle("Pas de titre");
		zBook1.setEdition("Pas d'édition");
		zBook1.setPagesNumber(100);
		zBook1.setLanguage(Language.EN);
		zBook1.setCollection(ZenikaCollection.NANTES);
		
		zBook2 = new ZBook();
		zBook2.setISBN(65);
		zBook2.setTitle("Pas de titre 2");
		zBook2.setEdition("Pas d'édition n°2");
		zBook2.setPagesNumber(150);
		zBook2.setLanguage(Language.FR);
		zBook2.setCollection(ZenikaCollection.SBR);

		author1 = new Author();
		author1.setFirstName("John");
		author1.setLastName("Doe");
		author2 = new Author();
		author2.setFirstName("Pierre");
		author2.setLastName("Dupont");
		
		ArrayList<Author> authorsZBook1 = new ArrayList<Author>();
		authorsZBook1.add(author1);
		authorsZBook1.add(author2);
		zBook1.setAuthors(authorsZBook1);
		
		ArrayList<Author> authorsZBook2 = new ArrayList<Author>();
		authorsZBook2.add(author1);
		zBook2.setAuthors(authorsZBook2);
	}

	@Test
	public void testCreateAndFind() {
		zBookService.createOrUpdate(zBook1);
		zBookService.createOrUpdate(zBook2);
		ZBook zBookFromDB = zBookService.findByIsbn(zBook1.getISBN());
		
		LOG.info("Comparing the data in the zBook1 and the data in the zBook in the DB");
		Assertions.assertThat(zBookFromDB).isEqualToIgnoringGivenFields(zBook1, "authors");
		List<Author> authors = zBook1.getAuthors();
		List<Author> authorsFromDb = zBookFromDB.getAuthors();
		for (int i=0; i<authors.size(); i++) {
			Assertions.assertThat(authorsFromDb.get(i)).isEqualToComparingFieldByField(authors.get(i));
		}
		
	}
	
	@Test
	public void testFindAll() {
		List<ZBook> zBooksInDb = zBookService.findAll();
		
		LOG.info("Checking that there is nothing in the DB");
		Assertions.assertThat(zBooksInDb).isEmpty();
		
		zBookService.createOrUpdate(zBook1);
		zBookService.createOrUpdate(zBook2);
		zBook2.setTitle("Pas de titre 2bis");
		zBookService.createOrUpdate(zBook2);
		zBooksInDb = zBookService.findAll();
		
		LOG.info("Checking that there is the right amount of data in the DB");
		Assertions.assertThat(zBooksInDb.size()).isEqualTo(2);
	}
	
	@After
	public void emptyDB() {
		this.zBookService.deleteAll();
	}

}
