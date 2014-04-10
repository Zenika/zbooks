package com.zenika.zbooks.persistence;
import com.googlecode.flyway.core.Flyway;
import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZBookOfTheMonth;
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
public class ZBookOfTheMonthMapperTest {
	
	 @Autowired
	    private ZBookOfTheMonthMapper zBookOfTheMonthMapper;

	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	    
	    @Test
	    public void test_that_getAllBooks_returns_all_books(){
	    	List<ZBookOfTheMonth> res = zBookOfTheMonthMapper.getAllBooksOfTheMonth();
	    	assertThat(res).hasSize(1);
	    	
	    	//('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',10,DATE '2000-10-13','EN',1);
	    	assertThat(res.get(0).getBook()).isNotNull();
	    	assertThat(res.get(0).getSubmitter()).isNotNull();
	    	assertThat(res.get(0).getBook().getTitle()).isEqualTo("Professional NoSQL");
	    	assertThat(res.get(0).getSubmitter().getUserName()).isEqualTo("Root");
	    	assertThat(res.get(0).getSupporters()).isEqualTo(1);
	    	
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
