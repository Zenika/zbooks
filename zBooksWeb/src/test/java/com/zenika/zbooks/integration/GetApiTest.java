package com.zenika.zbooks.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.Cookie;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.zenika.zbooks.IntegrationTest;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.persistence.UserCacheDAO;
import com.zenika.zbooks.persistence.ZBooksMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDatabaseContext.xml"})
@Category(IntegrationTest.class)
public class GetApiTest implements IntegrationTest {

    private static final Log LOG = LogFactory.getLog(GetApiTest.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ZBooksMapper zBooksMapper;
    
    @Autowired
    private DriverManagerDataSource dataSource;

    @Before
    public void initializeData() throws Exception {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            String sql = FileUtils.readFileToString(new File(GetApiTest.class.getClassLoader().getResource("initH2.sql").getFile()));
            statement.execute(sql);
            conn.commit();
            conn.close();
            LOG.info("H2 Inited");
        } catch (Exception e) {
            LOG.error("H2 Init : failed.",e);
            Assert.fail();
        }
        this.mockMvc = webAppContextSetup(this.wac).build();
        UserCacheDAO.getInstance().authenticateNewUser("tokenTest", ZPower.USER);
        UserCacheDAO.getInstance().authenticateNewUser("tokenRoot", ZPower.ADMIN);
    }

    @Test
    public void getListAsHTML() throws Exception {
        this.mockMvc.perform(get("/api").accept(MediaType.TEXT_HTML).cookie(new Cookie("token", "tokenTest")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getList() throws Exception {
        this.mockMvc.perform(get("/api/book").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenTest")))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.[0][id]").value(1)).andExpect(jsonPath("$.[2][edition]").value("Pearson"));
    }


    @Test
    public void getBook() throws Exception {
        this.mockMvc.perform(get("/api/book/2").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenTest")))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.[authors]").value("auteur2"));
    }
    
    @Test
    public void testGetBookWithoutAuthFail () throws Exception {
    	this.mockMvc.perform(get("/api/book/2").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
    }
    
    @Test
    public void testDeleteBookWithoutAdminPowerFail () throws Exception {
    	this.mockMvc.perform(delete("/api/book/2").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenTest")))
        .andExpect(status().is(403));
    }
    
    @Test
    public void testDeleteBookWithAdminPowerSucceed () throws Exception {
    	this.mockMvc.perform(delete("/api/book/2").accept(MediaType.APPLICATION_JSON).cookie(new Cookie("token", "tokenRoot")))
        .andExpect(status().isOk());
    	assertThat(zBooksMapper.getBook(2)).isNull();
    }
}
