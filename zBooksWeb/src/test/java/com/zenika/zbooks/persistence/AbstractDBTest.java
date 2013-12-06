package com.zenika.zbooks.persistence;

import com.zenika.zbooks.utils.ZBooksBddTool;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract class AbstractDBTest {

    private static final Logger LOG = Logger.getLogger(AbstractDBTest.class);

    @Autowired
    protected DriverManagerDataSource dataSource;

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
}
