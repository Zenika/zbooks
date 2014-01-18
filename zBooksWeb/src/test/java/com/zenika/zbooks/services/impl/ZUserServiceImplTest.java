package com.zenika.zbooks.services.impl;

import com.zenika.zbooks.UnitTest;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.persistence.ZUserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class ZUserServiceImplTest implements UnitTest {

    @InjectMocks
    private ZUserServiceImpl zUserService = new ZUserServiceImpl();
    @Mock
	private ZUserMapper zUserMapperMock;

    @Mock
    private ZBooksMapper zBooksMapper;
    @Mock
	private ServerCache serverCache;

	@Before
	public void setUp() throws Exception {
        when(zUserMapperMock.getZUser(anyString(), anyString())).thenReturn(new ZUser());
    }

	@Test
	public void connectZUserTest() {

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
		
		user2 = new ZUser ();
		user2.setEmail("userTest@test.fr");
		user2.setPassword("pwd");
		token2 = zUserService.connectZUser(user2);
		
		assertThat(token2).isNotEqualTo(token1);
	}

    @Test
    public void borrowBook_should_call_mapper() {
        ZUser zUser = new ZUser();
        ZBook zBook = new ZBook();
        zUserService.borrowBook(zUser, zBook);
        verify(zBooksMapper).borrowBook(zBook, zUser);
    }

    @Test
    public void returnBook_should_call_mapper() {
        int book_id = 2;
        String token = "";
        ZUser zUser = new ZUser();
        zUser.setId(2);
        when(serverCache.getZUser(token)).thenReturn(zUser);
        ZBook zBook = new ZBook();
        when(zBooksMapper.getBook(book_id)).thenReturn(zBook);
        when(zUserMapperMock.hasBorrowedBook(zUser.getId(), book_id)).thenReturn(true);
        zUserService.returnBook(token, book_id);
        verify(zBooksMapper).returnBook(any(ZBook.class));
    }


    @Test
    public void canReturnBook_should_return_true_if_user_is_ADMIN() {
        String token = "";
        int idZBook = 2;

        ZUser zUser = new ZUser();
        zUser.setZPower(ZPower.ADMIN);

        when(serverCache.getZUser(token)).thenReturn(zUser);

        boolean returned_result = zUserService.canReturnBook(token, idZBook);
        assertThat(returned_result).isTrue();
    }

    @Test
    public void canReturnBook_should_call_mapper_if_user_is_not_ADMIN() {
        String token = "";
        int idZBook = 2;

        ZUser zUser = new ZUser();
        zUser.setId(1);
        zUser.setZPower(ZPower.USER);

        when(serverCache.getZUser(token)).thenReturn(zUser);

        zUserService.canReturnBook(token, idZBook);
        verify(zUserMapperMock).hasBorrowedBook(zUser.getId(), idZBook);
    }
}
