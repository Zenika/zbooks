package com.zenika.zbooks.services;

import com.zenika.zbooks.entity.ZUser;

public interface ZUserService {

	void addZUser (ZUser user);
	
	boolean isZUserInDb (ZUser user);
	
	void deleteZUser (ZUser user);
	
	boolean isZUserAuthenticated (String userName, String token);
	
	String connectZUser (ZUser user);
	
	ZUser getZUser (String userName, String password);
}
