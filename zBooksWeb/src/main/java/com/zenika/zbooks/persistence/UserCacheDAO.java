package com.zenika.zbooks.persistence;

import java.util.HashMap;
import java.util.Map;

public class UserCacheDAO {

	private static UserCacheDAO instance;
	private static Map<String, String> usersAuthenticated;
	
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
	
	public void authenticateNewUser (String userName, String token) {
		usersAuthenticated.put(userName, token);
	}
	
	public boolean isUserAuthenticated (String userName, String token) {
		boolean isAuthenticated = usersAuthenticated.containsKey(userName);
		if (isAuthenticated) {
			isAuthenticated = usersAuthenticated.get(userName).equals(token);
		}
		return isAuthenticated;
		
	}
}
