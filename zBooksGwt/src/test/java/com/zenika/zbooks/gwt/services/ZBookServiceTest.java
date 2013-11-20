package com.zenika.zbooks.gwt.services;

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

import com.zenika.zbooks.gwt.client.entity.ZBook;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ZBookServiceTest {

	@Autowired
	private ZBookService zBookService;
	private ZBook zBook1;
	private ZBook zBook2;
	
	private static final Logger LOG = Logger.getLogger(ZBookServiceTest.class);
	
	
	@Before
	public void setUp() {
		//TODO to change if we add more attributes in the ZBook class
		zBook1 = new ZBook();
		zBook1.setISBN(42);
		zBook1.setTitle("Pas de titre");
		zBook1.setEdition("Pas d'édition");
		zBook1.setPagesNumber(100);
		
		zBook2 = new ZBook();
		zBook2.setISBN(65);
		zBook2.setTitle("Pas de titre 2");
		zBook2.setEdition("Pas d'édition n°2");
		zBook2.setPagesNumber(150);

	}

	@Test
	public void testCreateAndFind() {
		zBookService.createOrUpdate(zBook1);
		zBookService.createOrUpdate(zBook2);
		ZBook zBookFromDB = zBookService.findByIsbn(zBook1.getISBN());
		
		LOG.info("Comparing the data in the zBook1 and the data in the zBook in the DB");
		Assertions.assertThat(zBookFromDB.getISBN()).isEqualTo(zBook1.getISBN());
		Assertions.assertThat(zBookFromDB.getEdition()).isEqualTo(zBook1.getEdition());
		Assertions.assertThat(zBookFromDB.getPagesNumber()).isEqualTo(zBook1.getPagesNumber());
		Assertions.assertThat(zBookFromDB.getTitle()).isEqualTo(zBook1.getTitle());
	}
	
	@Test
	public void testFindAll() {
		zBookService.createOrUpdate(zBook1);
		zBookService.createOrUpdate(zBook2);
		zBook2.setTitle("Pas de titre 2bis");
		zBookService.createOrUpdate(zBook2);
		List<ZBook> zBooksInDb = zBookService.findAll();
		
		LOG.info("Checking that there is the right amount of data in the DB");
		Assertions.assertThat(zBooksInDb.size()).isEqualTo(2);
	}
	
	@After
	public void emptyDB() {
		this.zBookService.deleteAll();
	}

}
