package com.zenika.zbooks.persistence;

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.utils.ZBooksBddTool;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Category(UnitTest.class)
public class ZUserMapperTest extends AbstractDBTest implements UnitTest {

    private static final Logger LOG = Logger.getLogger(ZBooksMapperTest.class);

    @Autowired
    private ZUserMapper zUserMapper;


    @Before
    public void initializeData() {
        try {
            ZBooksBddTool.initializeData(dataSource);
            LOG.info("H2 Inited");
        } catch (Exception e) {
            LOG.error("H2 Init : failed.", e);
            Assert.fail();
        }
    }

    @Test
    public void getZUserTest() throws NoSuchAlgorithmException {
        ZUser user = zUserMapper.getZUser("root@zenika.com", hashPasswordInSHA256("pwd"));
        assertEquals("root@zenika.com", user.getEmail());
        assertEquals(hashPasswordInSHA256("pwd"), user.getPassword());
        assertEquals(1, user.getId());
        assertEquals(ZPower.ADMIN, user.getZPower());
    }

    @Test
    public void addZUserTest() {
        ZUser user = new ZUser();
        user.setEmail("userTest@zenika.com");
        user.setPassword("pwdTest");
        user.setZPower(ZPower.USER);
        zUserMapper.addZUser(user);
        assertNotNull(zUserMapper.getZUser(user.getEmail(), user.getPassword()));
    }

    @Test
    public void deleteZUserTest() throws NoSuchAlgorithmException {
        zUserMapper.deleteZUser(1);
        assertNull(zUserMapper.getZUser("root", hashPasswordInSHA256("pwd")));
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
}
