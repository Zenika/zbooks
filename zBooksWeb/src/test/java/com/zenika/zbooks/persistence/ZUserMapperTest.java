package com.zenika.zbooks.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;

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

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDatabaseContext.xml"})
@Category(UnitTest.class)
public class ZUserMapperTest implements UnitTest {

    private static final Log LOG = LogFactory.getLog(ZBooksMapperTest.class);

    @Autowired
    private ZUserMapper zUserMapper;

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
    public void getZUserTest () throws NoSuchAlgorithmException {
    	ZUser user = zUserMapper.getZUser("root", hashPasswordInSHA256("pwd"));
    	assertEquals("root", user.getUserName());
    	assertEquals(hashPasswordInSHA256("pwd"), user.getPassword());
    	assertEquals(1, user.getId());
    	assertEquals(ZPower.ADMIN, user.getZPower());
    }
    
    @Test
    public void addZUserTest () {
    	ZUser user = new ZUser();
    	user.setUserName("userTest");
    	user.setPassword("pwdTest");
    	user.setZPower(ZPower.USER);
    	zUserMapper.addZUser(user);
    	
    	assertNotNull(zUserMapper.getZUser(user.getUserName(), user.getPassword()));
    }
    
    @Test
    public void deleteZUserTest () throws NoSuchAlgorithmException {
    	zUserMapper.deleteZUser(1);
    	assertNull(zUserMapper.getZUser("root", hashPasswordInSHA256("pwd")));
    }
    
    private String hashPasswordInSHA256(String password) throws NoSuchAlgorithmException {
    	
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
    }
}
