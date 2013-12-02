package com.zenika.zbooks.persistence;

import java.util.HashMap;
import java.util.Map;

import com.zenika.zbooks.entity.ZPower;

public class UserCacheDAO {

	private static UserCacheDAO instance;
	private static Map<String, ZPower> usersAuthenticated;
	
	private UserCacheDAO () {
		usersAuthenticated = new HashMap<>();
	}
	
	public static UserCacheDAO getInstance () {
		if (instance == null) {
			synchronized(UserCacheDAO.class) {
				instance = new UserCacheDAO();
			}
		}
		return instance;
	}
	
	public void authenticateNewUser (String token, ZPower power) {
		usersAuthenticated.put(token, power);
	}
	
	public ZPower getUserAccess (String token) {
		if (usersAuthenticated.containsKey(token)) {
			return usersAuthenticated.get(token);
		} else {
			return ZPower.USER;
		}
	}
	
	public boolean isUserAuthenticated (String token) {
		
		return usersAuthenticated.containsKey(token);
		
	}
}
