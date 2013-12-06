package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZPower;

public interface ServerCache {

	void authenticateNewUser (String token, ZPower power);
	ZPower getUserAccess (String token);
	boolean isUserAuthenticated (String token);
	boolean isNonceAlreadyStored(String nonce);
	void storeNonceInCache(String nonce);
}
