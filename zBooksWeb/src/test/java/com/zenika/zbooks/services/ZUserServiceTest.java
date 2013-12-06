package com.zenika.zbooks.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;
import com.zenika.zbooks.persistence.ZUserMapper;
import com.zenika.zbooks.services.impl.ZUserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Category(UnitTest.class)
public class ZUserServiceTest implements UnitTest {

	private ZUserMapper zUserMapperMock;
	private ServerCache serverCacheMock;
	private ZUserServiceImpl zUserService = new ZUserServiceImpl();
	
	@Before
	public void setUp() throws Exception {
		zUserMapperMock = Mockito.mock(ZUserMapper.class);
		serverCacheMock = Mockito.mock(ServerCache.class);
		ReflectionTestUtils.setField(zUserService, "zUserMapper", zUserMapperMock);
		ReflectionTestUtils.setField(zUserService, "serverCache", serverCacheMock);
	}

	@Test
	public void connectZUserTest() {
		when(zUserMapperMock.getZUser(any(String.class), any(String.class))).thenReturn(new ZUser ());
		
		ZUser user = new ZUser();
		user.setEmail("userTest@test.fr");
		user.setPassword("pwd");
		String token1 = zUserService.connectZUser(user);
		
		assertThat(token1).isNotNull();
		
		ZUser user2 = new ZUser ();
		user2.setEmail("userTest2@test.fr");
		user2.setPassword("pwd");
		String token2 = zUserService.connectZUser(user2);
		
		assertThat(token2).isNotEqualTo(token1);
	}

}
