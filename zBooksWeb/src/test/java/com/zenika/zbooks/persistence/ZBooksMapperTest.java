package com.zenika.zbooks.persistence;

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZBook;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDatabaseContext.xml"})
@Category(UnitTest.class)
public class ZBooksMapperTest implements UnitTest {

    private static final Log LOG = LogFactory.getLog(ZBooksMapperTest.class);

    @Autowired
    private ZBooksMapper zBooksMapper;

    @Autowired
    private DriverManagerDataSource dataSource;

    @Before
    public void initializeData() {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            String sql = FileUtils.readFileToString(new File(ZBooksMapperTest.class.getClassLoader().getResource("initH2.sql").getFile()));
            statement.execute(sql);
            conn.commit();
            conn.close();
            LOG.info("H2 Inited");
        } catch (Exception e) {
            LOG.error("H2 Init : failed.", e);
            Assert.fail();
        }
    }

    @Test
    public void getBookTest() {

        ZBook zBook = zBooksMapper.getBook(1);
        assertEquals("047094224X", zBook.getISBN());
        assertEquals("Professional NoSQL", zBook.getTitle());
        assertEquals("auteur1", zBook.getAuthors());
        assertEquals("John Wiley & Sons Ltd", zBook.getEdition());
        assertEquals("EN", zBook.getLanguage());

        assertEquals("047094224X", zBook.getISBN());
        zBook = zBooksMapper.getBook(2);
        assertEquals("0596518846X", zBook.getISBN());
        zBook = zBooksMapper.getBook(3);
        assertEquals("2744025828X", zBook.getISBN());
        zBook = zBooksMapper.getBook(4);
        assertNull(zBook);
    }

    @Test
    public void getBooksTest() {
        List<ZBook> zBook = zBooksMapper.getBooks();
        assertEquals(3, zBook.size());
        assertTrue(checkIdUnicity(zBook));
    }

    @Test
    public void deleteBookTest() {
        assertEquals(3, zBooksMapper.getBooks().size());
        zBooksMapper.deleteBook(2);
        assertNull(zBooksMapper.getBook(2));
        assertEquals(2, zBooksMapper.getBooks().size());
    }

    @Test
    public void addBookTest() {
        ZBook zBook = new ZBook();
        zBook.setISBN("ISBN_TEST");
        zBook.setTitle("Professional NoSQL");
        zBook.setAuthors("auteur1");
        zBook.setEdition("edition");
        zBook.setLanguage("EN");

        assertEquals(3, zBooksMapper.getBooks().size());
        zBooksMapper.addBook(zBook);
        assertNotNull(zBooksMapper.getBook(4));
        assertEquals(4, zBooksMapper.getBooks().size());
    }

    @Test
    public void upadateBookTest() {

        ZBook zBook = zBooksMapper.getBook(1);
        assertEquals("047094224X", zBook.getISBN());
        assertEquals("Professional NoSQL", zBook.getTitle());
        assertEquals("auteur1", zBook.getAuthors());
        assertEquals("John Wiley & Sons Ltd", zBook.getEdition());
        assertEquals("EN", zBook.getLanguage());

        zBook.setISBN("ISBN_VAL");
        zBook.setAuthors("AUTHOR_VAL");
        zBook.setEdition("EDITION_VAL");
        zBook.setLanguage("TS");
        zBook.setPagesNumber(11);
        zBook.setReleaseDate("25/10/2013");
        zBook.setTitle("TITLE_VAL");

        zBooksMapper.updateBook(zBook);
        zBook = zBooksMapper.getBook(1);

        assertEquals("ISBN_VAL", zBook.getISBN());
        assertEquals("AUTHOR_VAL", zBook.getAuthors());
        assertEquals("EDITION_VAL", zBook.getEdition());
        assertEquals("TS", zBook.getLanguage());
        assertEquals(11, zBook.getPagesNumber());
        assertEquals("TITLE_VAL", zBook.getTitle());
        assertEquals("25/10/2013", zBook.getReleaseDate());

    }

    private boolean checkIdUnicity(List<ZBook> zBooks) {
        HashSet monHashSet = new HashSet();
        for (ZBook zbook : zBooks) {
            if (!monHashSet.add(zbook.getId()))
                return false;
        }
        return true;
    }


}
