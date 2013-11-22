package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ZBooksMapperTest {

	private static Log log = LogFactory.getLog(ZBooksMapperTest.class);
	
	@Autowired
	private ZBooksMapper zBooksMapper;
	
	private ZBook zBook;

	@Before
	public void initializeData () {
		
		zBook = new ZBook();
		zBook.setAuthors("Craig, Walls");
		zBook.setEdition("Paperback");
		zBook.setISBN("1933988134");
		zBook.setLanguage("EN");
		zBook.setPagesNumber(650);
		zBook.setReleaseDate(new Date());
		zBook.setTitle("Spring in Action");
		if (zBooksMapper.getBook(zBook.getId()) != null) {
			zBooksMapper.deleteBook(zBook.getId());
		}
		log.debug("Created the zBook : " + zBook);
	}

	@Test
	public void addBookTest() {
		zBooksMapper.addBook(zBook);
		System.out.println(zBooksMapper.getBook(zBook.getId()));
	}
}
