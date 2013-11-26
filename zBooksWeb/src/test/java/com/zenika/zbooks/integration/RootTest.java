package com.zenika.zbooks.integration;

import com.zenika.zbooks.IntegrationTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDatabaseContext.xml"})
@Category(IntegrationTest.class)
public class RootTest implements IntegrationTest{

    private static final Log LOG = LogFactory.getLog(RootTest.class);

    @Autowired
    private WebApplicationContext wac;

    static WebDriver driver;

    static String appPath;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        driver = new HtmlUnitDriver();
        appPath = "http://localhost:8080/";
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        driver.quit();
    }

    @Test
    public void getRootAsAPI() throws Exception {
        MockMvc mockMvc = webAppContextSetup(this.wac).build();
        mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void getRootAsHTML() throws Exception {
        driver.get(appPath);
        assertEquals("Zenika Books",driver.getTitle());
    }

}
