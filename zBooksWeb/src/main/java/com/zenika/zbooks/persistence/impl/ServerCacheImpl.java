package com.zenika.zbooks.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;

@Service
@Scope("singleton")
public class ServerCacheImpl implements ServerCache {

	private Map<String, ZUser> usersAuthenticated;
	private Map<String, Long> nonceCache;
	private final long ONE_HOUR = 3600000L;
	
	public ServerCacheImpl () {
		usersAuthenticated = new HashMap<>();
		nonceCache = new HashMap<>();
	}
	
	//Delete the nonces cached more than 1 hour ago
	@Scheduled(initialDelay=ONE_HOUR, fixedRate=ONE_HOUR)
	private void emptyNonceCache () {
		for (String key : nonceCache.keySet()) {
			long dateOfTheRegistration = nonceCache.get(key);
			if (dateOfTheRegistration + ONE_HOUR <= System.currentTimeMillis()) {
				nonceCache.remove(key);
			}
		}
	}
	
	@Override
	public void authenticateNewUser (String token, ZUser zUser) {
		usersAuthenticated.put(token, zUser);
	}
	
	@Override
	public ZPower getUserAccess (String token) {
		if (usersAuthenticated.containsKey(token)) {
			return usersAuthenticated.get(token).getZPower();
		} else {
			return ZPower.USER;
		}
	}
	
	@Override
	public boolean isUserAuthenticated (String token) {		
		return usersAuthenticated.containsKey(token);
	}
	
	@Override
	public boolean isNonceAlreadyStored(String nonce) {
		return nonceCache.containsKey(nonce);
	}
	
	@Override
	public void storeNonceInCache(String nonce) {
		nonceCache.put(nonce, System.currentTimeMillis());
	}

	@Override
	public ZUser getZUser(String token) {
		return usersAuthenticated.get(token);
	}

	@Override
	public void disconnectZUser(String token) {
		usersAuthenticated.remove(token);
	}
}
