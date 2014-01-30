package com.zenika.zbooks.persistence;

import com.googlecode.flyway.core.Flyway;
import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZCollection;
import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Category(UnitTest.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ZBooksMapperTest implements UnitTest {

    @Autowired
    private ZBooksMapper zBooksMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getBook_should_return_a_book() {

        ZBook zBook = zBooksMapper.getBook(1);

        assertThat(zBook.getISBN()).isEqualTo("047094224X");
        assertThat(zBook.getTitle()).isEqualTo("Professional NoSQL");
        assertThat(zBook.getAuthors()).isEqualTo("auteur1");
        assertThat(zBook.getEdition()).isEqualTo("John Wiley & Sons Ltd");
        assertThat(zBook.getLanguage()).isEqualTo("EN");
        assertThat(zBook.isBorrowed()).isFalse();
    }


    @Test
    public void getBook_should_return_a_borrowed_book() {
        this.jdbcTemplate.execute("INSERT INTO ZBOOKS_BORROWED (idBook, idBorrower, borrow_date) VALUES(2, 2, SYSDATE())");

        ZBook zBook = zBooksMapper.getBook(2);
        assertThat(zBook.isBorrowed()).isTrue();
    }

    @Test
    public void getBook_should_return_an_available_book_if_book_has_been_returned() {
        this.jdbcTemplate.execute("INSERT INTO ZBOOKS_BORROWED (idBook, idBorrower, borrow_date, return_date) VALUES(2, 2, SYSDATE(), SYSDATE())");

        ZBook zBook = zBooksMapper.getBook(2);
        assertThat(zBook.isBorrowed()).isFalse();
    }

    @Test
    public void getBooks_should_return_all_books() {
        List<ZBook> zBooks = zBooksMapper.getBooks();
        assertThat(zBooks).hasSize(3);
    }

    @Test
    public void getBooksOfPage_should_return_all_books_of_the_page() {
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");
        this.jdbcTemplate.execute("INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN')");

        List<ZBook> zBooks = zBooksMapper.getBooksOfPage(0,5, "", "");
        assertThat(zBooks).hasSize(5);
    }

    @Test
    public void deleteBook_should_delete_a_book() {
        assertThat(zBooksMapper.getBooks()).hasSize(3);
        zBooksMapper.deleteBook(2);
        assertThat(zBooksMapper.getBooks()).hasSize(2);

    }

    @Test
    public void addBook_should_add_a_book() {
        ZBook zBook = new ZBook();
        zBook.setISBN("ISBN_TEST");
        zBook.setTitle("Professional NoSQL");
        zBook.setAuthors("auteur1");
        zBook.setEdition("edition");
        zBook.setLanguage("EN");
        zBook.setZCollection(ZCollection.SBR);

        assertThat(zBooksMapper.getBooks()).hasSize(3);
        zBooksMapper.addBook(zBook);
        assertThat(zBooksMapper.getBooks()).hasSize(4);
        assertThat(zBooksMapper.getBook(4).getZCollection()).isEqualTo(ZCollection.SBR);
    }

    @Test
    public void updateBook_should_update_a_book() {

        ZBook zBook = zBooksMapper.getBook(1);
        assertThat(zBook.getISBN()).isEqualTo("047094224X");
        assertThat(zBook.getTitle()).isEqualTo("Professional NoSQL");
        assertThat(zBook.getAuthors()).isEqualTo("auteur1");
        assertThat(zBook.getEdition()).isEqualTo("John Wiley & Sons Ltd");
        assertThat(zBook.getLanguage()).isEqualTo("EN");

        zBook.setISBN("ISBN_VAL");
        zBook.setAuthors("AUTHOR_VAL");
        zBook.setEdition("EDITION_VAL");
        zBook.setLanguage("TS");
        zBook.setPagesNumber(11);
        zBook.setReleaseDate("25/10/2013");
        zBook.setTitle("TITLE_VAL");

        zBooksMapper.updateBook(zBook);
        zBook = zBooksMapper.getBook(1);

        assertThat(zBook.getISBN()).isEqualTo("ISBN_VAL");
        assertThat(zBook.getTitle()).isEqualTo("TITLE_VAL");
        assertThat(zBook.getAuthors()).isEqualTo("AUTHOR_VAL");
        assertThat(zBook.getEdition()).isEqualTo("EDITION_VAL");
        assertThat(zBook.getLanguage()).isEqualTo("TS");
        assertThat(zBook.getPagesNumber()).isEqualTo(11);
        assertThat(zBook.getReleaseDate()).isEqualTo("25/10/2013");
    }

    @Test
    public void borrowBook_should_add_a_borrowed_book_with_a_borrowed_date() {
        ZBook book = zBooksMapper.getBook(1);

        ZUser user = new ZUser();
        user.setId(2);
        zBooksMapper.borrowBook(book, user);
        Integer numberOfBorrowedBook = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ZBOOKS_BORROWED WHERE IDBOOK=?", new Object[]{1}, Integer.class);
        assertThat(numberOfBorrowedBook).isEqualTo(1);
    }

    @Test
    public void returnBook_should_update_a_borrowed_book_with_a_returned_date() {

        this.jdbcTemplate.execute("INSERT INTO ZBOOKS_BORROWED (idBook, idBorrower, borrow_date) VALUES(1, 2, SYSDATE())");
        ZBook book = zBooksMapper.getBook(1);

        ZUser user = new ZUser();
        user.setId(2);
        zBooksMapper.returnBook(book);
        Date returned_date = jdbcTemplate.queryForObject("SELECT return_date FROM ZBOOKS_BORROWED WHERE IDBOOK=?", new Object[]{1}, Date.class);
        assertThat(returned_date).isNotNull();
    }

    @Deprecated
    private boolean checkIdUnicity(List<ZBook> zBooks) {
        HashSet<Integer> monHashSet = new HashSet<>();
        for (ZBook zbook : zBooks) {
            if (!monHashSet.add(zbook.getId()))
                return false;
        }
        return true;
    }


    @Configuration
    @MapperScan(basePackages = "com.zenika.zbooks.persistence", annotationClass = Repository.class)
    public static class TestConfiguration {

        @Bean(initMethod = "migrate")
        public Flyway flyway() {
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource());
            flyway.setInitOnMigrate(true);
            return flyway;
        }

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(H2)
                                                .build();
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory.setConfigLocation(new ClassPathResource("com/zenika/zbooks/mybatis-config.xml"));
            return sessionFactory.getObject();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

    }

}
