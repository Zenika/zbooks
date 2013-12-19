package com.zenika.zbooks.utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ZBooksBddTool {

    private static final Logger LOG = Logger.getLogger(ZBooksBddTool.class);

    public static void main(String... args) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext();
        appContext.getEnvironment().setActiveProfiles("dev");
        appContext.setConfigLocation("databaseContext.xml");
        appContext.refresh();
        DataSource dataSource = (DriverManagerDataSource) appContext.getBean("dataSource");
        try {
            initializeData(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        appContext.close();
    }

    public static void initializeData(DataSource dataSource) throws SQLException, IOException {
        Connection conn = dataSource.getConnection();
        Statement statement = conn.createStatement();
        String sql = FileUtils.readFileToString(new File(ZBooksBddTool.class.getClassLoader().getResource("initH2.sql").getFile()));
        statement.execute(sql);
        conn.commit();
        conn.close();
    }
}
