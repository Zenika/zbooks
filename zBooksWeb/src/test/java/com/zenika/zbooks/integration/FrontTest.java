package com.zenika.zbooks.integration;

import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.concurrent.TimeUnit;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.thoughtworks.selenium.Selenium;
import com.zenika.zbooks.IntegrationTest;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.persistence.UserCacheDAO;
import com.zenika.zbooks.utils.ZBooksUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDatabaseContext.xml"})
@Category(IntegrationTest.class)
public class FrontTest implements IntegrationTest {

    private static final Log LOG = LogFactory.getLog(FrontTest.class);

    @Autowired
    private WebApplicationContext wac;
    
    private static FirefoxDriver driver;

    private static String appPath;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        appPath = "http://localhost:8080/";
    }

    @Before
    public void cleanCache() {
        driver.manage().deleteAllCookies();
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
        assertEquals("Zenika Books", driver.getTitle());
    }


    @Test
    public void getBookErrorAsHTML() throws Exception {
        driver.get(appPath + "/2");
        assertThat(driver.findElement(By.tagName("h1")).getText()).contains(" 404 ");
    }

    @Test
    public void getBookAsHTML() throws Exception {
    	driver.get(appPath);
    	waitForAngularRequestsToFinish(driver);
    	driver.findElement(By.id("inputUserName")).sendKeys("root@zenika.com");
    	driver.findElement(By.id("inputPassword")).sendKeys("pwd");
    	driver.findElementById("logInBtn").click();
    	waitForAngularRequestsToFinish(driver);
        driver.get(appPath + "/#2");
        waitForAngularRequestsToFinish(driver);
        assertEquals(driver.findElement(By.tagName("h3")).getText(), "SQL in a Nutshell");
    }

}
