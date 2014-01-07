package com.zenika.zbooks.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Category(UnitTest.class)
public class ZBooksMapperTest extends AbstractDBTest implements UnitTest {

    private static final Logger LOG = Logger.getLogger(ZBooksMapperTest.class);

    @Autowired
    private ZBooksMapper zBooksMapper;


    @Test
    public void getBookTest() {

        ZBook zBook = zBooksMapper.getBook(1);
        assertEquals("047094224X", zBook.getISBN());
        assertEquals("Professional NoSQL", zBook.getTitle());
        assertEquals("auteur1", zBook.getAuthors());
        assertEquals("John Wiley & Sons Ltd", zBook.getEdition());
        assertEquals("EN", zBook.getLanguage());
        assertEquals(ZCollection.NANTES, zBook.getZCollection());
        assertEquals("Root", zBook.getBorrowerName());

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
        zBook.setZCollection(ZCollection.SBR);

        assertEquals(3, zBooksMapper.getBooks().size());
        zBooksMapper.addBook(zBook);
        assertNotNull(zBooksMapper.getBook(4));
        assertEquals(4, zBooksMapper.getBooks().size());
        assertEquals(ZCollection.SBR, zBooksMapper.getBook(4).getZCollection());
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
