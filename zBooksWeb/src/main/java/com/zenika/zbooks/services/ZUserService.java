package com.zenika.zbooks.services;

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
}
