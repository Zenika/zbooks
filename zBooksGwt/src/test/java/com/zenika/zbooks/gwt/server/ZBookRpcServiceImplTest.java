package com.zenika.zbooks.gwt.server;

import java.io.Serializable;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;
import com.zenika.zbooks.gwt.services.ZBookService;



/**Used to check that all the returns are serializable
 * 
 * @author cjuste
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ZBookRpcServiceImplTest {

	@Autowired
	private ZBookRpcServiceImpl zBookRpcService;
	@Autowired
	private ZBookService zBookService;
	
	@Before
	public void setUp () {
		zBookService.createOrUpdate(9781933988139L, ZenikaCollection.SBR);
	}
	
	@Test
	public void testGetZBookReturnSerializable() {
		ZBook zBook = zBookRpcService.getZBook(42L);
		Assertions.assertThat(zBook instanceof Serializable);
	}
	
	@Test
	public void testGetAllZBooksReturnSerializable() {
		List<ZBook> zBooks = zBookRpcService.getAllZBooks();
		Assertions.assertThat(zBooks instanceof Serializable);
	}
	
	@Test
	public void testAddZBookReturnSerializable() {
		ZBook zBook = zBookRpcService.addZBook(9781933988139L, ZenikaCollection.RENNES);
		Assertions.assertThat(zBook instanceof Serializable);
	}

}
