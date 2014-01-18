package com.zenika.zbooks.persistence;

import com.googlecode.flyway.core.Flyway;
import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Category(UnitTest.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ZUserMapperTest implements UnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZBooksMapperTest.class);

    @Autowired
    private ZUserMapper zUserMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


//    @Before
//    public void initializeData() {
//        try {
//            ZBooksBddTool.initializeData(dataSource);
//            LOGGER.info("H2 Inited");
//        } catch (Exception e) {
//            LOGGER.error("H2 Init : failed.", e);
//            Assert.fail();
//        }
//    }

    @Test
    public void getZUser_should_returned_complete_ZUser() throws NoSuchAlgorithmException {
        ZUser user = zUserMapper.getZUser("root@zenika.com", hashPasswordInSHA256("pwd"));

        assertThat(user.getEmail()).isEqualTo("root@zenika.com");
        assertThat(user.getPassword()).isEqualTo(hashPasswordInSHA256("pwd"));
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getZPower()).isEqualTo(ZPower.ADMIN);
    }
    
    @Test
    public void getZUserWithEmail_should_returned_the_user_with_a_null_password() {
    	ZUser user = zUserMapper.getZUserWithEmail("root@zenika.com");

        assertThat(user.getEmail()).isEqualTo("root@zenika.com");
        assertThat(user.getPassword()).isNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getZPower()).isEqualTo(ZPower.ADMIN);
    }
    
    @Test
    @Ignore
    public void borrowOrReturnBookTest() {
    	//Test the data
    	ZUser user = zUserMapper.getZUserWithEmail("root@zenika.com");
//    	assertEquals(2, user.getBorrowedBooks().size());
//    	assertEquals(2, user.getBorrowedBooks().get(1).getId());
    	
    	//Test the return
//    	zUserMapper.returnBook(2, 0);
    	user = zUserMapper.getZUserWithEmail("root@zenika.com");
//    	assertEquals(1, user.getBorrowedBooks().size());
//    	assertNotEquals(2, user.getBorrowedBooks().get(0).getId());
    	
    	//Test the borrow
//    	zUserMapper.borrowBook(2, user.getId());
    	user = zUserMapper.getZUserWithEmail("root@zenika.com");
//    	assertEquals(2, user.getBorrowedBooks().size());
//    	assertEquals(2, user.getBorrowedBooks().get(1).getId());
    }

    @Test
    public void addZUser_should_add_a_user_in_db() {
        ZUser user = new ZUser();
        user.setEmail("userTest@zenika.com");
        user.setPassword("pwdTest");
        user.setZPower(ZPower.USER);
        zUserMapper.addZUser(user);
        assertThat(zUserMapper.getZUser(user.getEmail(), user.getPassword())).isNotNull();
    }

    @Test
    public void deleteZUser_should_remove_the_user_in_db() throws NoSuchAlgorithmException {
        zUserMapper.deleteZUser(1);
        assertThat(zUserMapper.getZUser("root", hashPasswordInSHA256("pwd"))).isNull();
    }

    @Test
    public void hasBorrowedBook_should_return_true_if_user_has_borrowed_the_book() {
        this.jdbcTemplate.execute("INSERT INTO ZBOOKS_BORROWED (idBook, idBorrower, borrow_date) VALUES(1, 2, SYSDATE())");

        ZUser user = new ZUser();
        user.setId(2);
        int bookId = 1;
        boolean hasBorrowedBook = zUserMapper.hasBorrowedBook(user.getId(), bookId);
        assertThat(hasBorrowedBook).isTrue();
    }

    @Test
    public void hasBorrowedBook_should_return_false_if_user_has_not_borrowed_the_book() {
        this.jdbcTemplate.execute("INSERT INTO ZBOOKS_BORROWED (idBook, idBorrower, borrow_date) VALUES(1, 2, SYSDATE())");

        ZUser user = new ZUser();
        user.setId(1);
        int bookId = 1;
        boolean hasBorrowedBook = zUserMapper.hasBorrowedBook(user.getId(), bookId);
        assertThat(hasBorrowedBook).isFalse();
    }


    private String hashPasswordInSHA256(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
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
