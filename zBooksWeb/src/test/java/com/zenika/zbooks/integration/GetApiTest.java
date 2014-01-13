package com.zenika.zbooks.integration;

import com.zenika.zbooks.IntegrationTest;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.persistence.ZUserMapper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Category(IntegrationTest.class)
public class GetApiTest implements IntegrationTest {

    private static final Logger LOG = Logger.getLogger(GetApiTest.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ZBooksMapper zBooksMapper;
    
    @Autowired
    private ZUserMapper zUserMapper;

    @Autowired
    private DriverManagerDataSource dataSource;
    
    @Autowired
    private ServerCache serverCache;

    @Before
    public void initializeData() {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            String sql = FileUtils.readFileToString(new File(GetApiTest.class.getClassLoader().getResource("initH2.sql").getFile()));
            statement.execute(sql);
            conn.commit();
            conn.close();
            LOG.info("H2 Inited");
        } catch (Exception e) {
            LOG.error("H2 Init : failed.", e);
            Assert.fail();
        }
        this.mockMvc = webAppContextSetup(this.wac).build();
        ZUser zUserTest = zUserMapper.getZUserWithEmail("user@zenika.com");
        ZUser zUserRoot = zUserMapper.getZUserWithEmail("root@zenika.com");
        serverCache.authenticateNewUser("tokenTest", zUserTest);
        serverCache.authenticateNewUser("tokenRoot", zUserRoot);
    }


    @Test
    public void testGetBookWithoutAuthFail() throws Exception {
        this.mockMvc.perform(get("/api/books/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteBookWithoutAdminPowerFail() throws Exception {
        this.mockMvc.perform(delete("/api/books/2").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenTest")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteBookWithAdminPowerSucceed() throws Exception {
        this.mockMvc.perform(delete("/api/books/2").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenRoot")))
                .andExpect(status().isNoContent());
        assertThat(zBooksMapper.getBook(2)).isNull();
    }
    
    @Test
    public void testBorrowBook () throws Exception {
    	this.mockMvc.perform(put("/api/books/2/return").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenRoot")))
        .andExpect(status().isOk());
    	ZUser zUserRoot = zUserMapper.getZUserWithEmail("root@zenika.com");
    	assertEquals(1, zUserRoot.getBorrowedBooks().size());
    	assertNotEquals(2, zUserRoot.getBorrowedBooks().get(0));
    	this.mockMvc.perform(put("/api/books/2/borrow").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenTest")))
        .andExpect(status().isOk());
    	ZUser zUserTest = zUserMapper.getZUserWithEmail("user@zenika.com");
    	ZBook zBook = zBooksMapper.getBook(2);
    	assertEquals(1, zUserTest.getBorrowedBooks().size());
    	assertEquals(zBook.getISBN(), zUserTest.getBorrowedBooks().get(0).getISBN());
    	assertTrue(zBook.getBorrowerName().equals(zUserTest.getUserName()));
    }
}
