package com.zenika.zbooks.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.UserCacheDAO;
import com.zenika.zbooks.persistence.ZUserMapper;
import com.zenika.zbooks.services.ZUserService;

@Service
public class ZUserServiceImpl implements ZUserService {

	@Autowired
	private ZUserMapper zUserMapper;
	
	private static final Logger LOG = Logger.getLogger(ZUserServiceImpl.class);
	
	private UserCacheDAO userCache = UserCacheDAO.getInstance();
	
	@Override
	public void addZUser(ZUser user) {
		zUserMapper.addZUser(user);
	}

	@Override
	public boolean isZUserInDb(ZUser user) {
		return zUserMapper.getZUser(user.getUserName(), user.getPassword()) != null;
	}

	@Override
	public void deleteZUser(ZUser user) {
		ZUser userInDb = zUserMapper.getZUser(user.getUserName(), user.getPassword());
		zUserMapper.deleteZUser(userInDb.getId());
	}

	@Override
	public boolean isZUserAuthenticated (String token) {
		return userCache.isUserAuthenticated(token);
	}

	@Override
	public String connectZUser(ZUser user) {
		ZUser userInDb = zUserMapper.getZUser(user.getUserName(), user.getPassword());
		if (userInDb != null) {
			try {
				String token = this.hashZUser(user);
				userCache.authenticateNewUser(token, userInDb.getZPower());
				return token;
			} catch (NoSuchAlgorithmException e) {
				LOG.error(e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	private String hashZUser (ZUser user) throws NoSuchAlgorithmException {
		String userName = user.getUserName();
		String password = user.getPassword();
		String messageToDigest = "\\" + userName + "/\\" + password + "/";
    	
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(messageToDigest.getBytes());
 
        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
	}

	@Override
	public ZUser getZUser(String userName, String password) {
		return zUserMapper.getZUser(userName, password);
	}
	
	@Override
	public ZPower getZUserAccess (String token) {
		return userCache.getUserAccess(token);
	}

}
