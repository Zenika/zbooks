package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;

public interface ServerCache {

	void authenticateNewUser (String token, ZUser zUser);
	ZPower getUserAccess (String token);
	ZUser getZUser (String token);
	boolean isUserAuthenticated (String token);
	boolean isNonceAlreadyStored(String nonce);
	void storeNonceInCache(String nonce);
}
