package com.zenika.zbooks.services;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ZUserService {
	
	void addZUser (ZUser user);
	
	boolean isZUserInDb (ZUser user);
	
	void deleteZUser (ZUser user);
	
	boolean isZUserAuthenticated (String token);
	
	String connectZUser (ZUser user);
	
	ZUser getZUser (String userName, String password);
	
	ZPower getZUserAccess (String token);
	
	ZUser getAuthenticatedZUser (String token);
	
	String connectUserWithGoogle (String returnToUrl, HttpServletRequest request, HttpServletResponse response);
	
	String checkAuthentification (HttpServletRequest request);
	
	boolean borrowBook (ZUser zUser, ZBook zBook);
	
	boolean returnBook (String token, int book_id);
	
	void disconnectZUser (String token);
	
	String getUserFirstName (String token);

	boolean canReturnBook(String token, int idZBook);

    ZUser getUser(int id);

    ZUser authenticateZUser(ZUser user);

    String createToken(ZUser user);
}
