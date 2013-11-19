package com.zenika.zbooks.gwt.services;

import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.zbooks.gwt.entity.ZBook;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ZBookServiceTest {

	@Autowired
	private ZBookService zBookService;
	private ZBook zBook;
	
	
	@Before
	public void setUp() {
		zBook = new ZBook();
		zBook.setISBN(42);
		zBook.setEdition("Pas d'édition");
		zBook.setPagesNumber(100);
	}

	@Test
	public void testCreate() {
		zBookService.create(zBook);
		ZBook zBookFromDB = zBookService.findByIsbn(zBook.getISBN());
		assertTrue("The ISBN of the book from the DB and the one we just added should be the same", zBook.getISBN() == zBookFromDB.getISBN());
	}

}
