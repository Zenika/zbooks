package com.zenika.zbooks.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;

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
	
	boolean returnBook (int book_id);
}
