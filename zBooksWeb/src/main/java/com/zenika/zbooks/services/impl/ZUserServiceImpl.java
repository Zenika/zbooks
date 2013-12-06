package com.zenika.zbooks.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Base64;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;
import com.zenika.zbooks.persistence.ZUserMapper;
import com.zenika.zbooks.services.ZUserService;

@Service
public class ZUserServiceImpl implements ZUserService {

	@Autowired
	private ZUserMapper zUserMapper;
	
	private static final Logger LOG = Logger.getLogger(ZUserServiceImpl.class);
	
	@Autowired
	private ServerCache serverCache;
	
	private OpenIdManager openIdManager = new OpenIdManager();
	
	private static final String RAW_MAC_COOKIE_KEY = "RawMacKey";
	private static final String ENDPOINT_ALIAS_KEY = "Alias";
	private static final String RETURN_TO_KEY = "ReturnTo";
	private static final long ONE_HOUR = 3600000L;
	
	
	@Override
	public void addZUser(ZUser user) {
		zUserMapper.addZUser(user);
	}

	@Override
	public boolean isZUserInDb(ZUser user) {
		return zUserMapper.getZUser(user.getEmail(), user.getPassword()) != null;
	}

	@Override
	public void deleteZUser(ZUser user) {
		ZUser userInDb = zUserMapper.getZUser(user.getEmail(), user.getPassword());
		zUserMapper.deleteZUser(userInDb.getId());
	}

	@Override
	public boolean isZUserAuthenticated (String token) {
		return serverCache.isUserAuthenticated(token);
	}

	@Override
	public String connectZUser(ZUser user) {
		ZUser userInDb = zUserMapper.getZUser(user.getEmail(), user.getPassword());
		if (userInDb != null) {
			try {
				String token = this.hashZUser(user);
				serverCache.authenticateNewUser(token, userInDb.getZPower());
				return token;
			} catch (NoSuchAlgorithmException e) {
				LOG.error(e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	private String hashZUser (ZUser user) throws NoSuchAlgorithmException {
		String email = user.getEmail();
		String password = user.getPassword();
		
		return hashStrings(email, password);
	}
	
	private String hashStrings (String... strings) throws NoSuchAlgorithmException {
		String messageToDigest = "";
		for (String string : strings) {
			messageToDigest += "\\" + string + "/";
		}
	    	
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
		return serverCache.getUserAccess(token);
	}

	@Override
	public String connectUserWithGoogle(String returnToUrl,
			HttpServletRequest request, HttpServletResponse response) {
		openIdManager.setReturnTo(returnToUrl);
		openIdManager.setRealm("http://localhost:8080/");
		
		Endpoint googleEndpoint = openIdManager.lookupEndpoint("Google");
		Association googleAssociation = openIdManager.lookupAssociation(googleEndpoint);
		
		try {
			response.addCookie(new Cookie(ZUserServiceImpl.RAW_MAC_COOKIE_KEY, URLEncoder.encode(googleAssociation.getMacKey(), "UTF-8")));
			response.addCookie(new Cookie(ZUserServiceImpl.ENDPOINT_ALIAS_KEY, URLEncoder.encode(googleEndpoint.getAlias(), "UTF-8")));
			response.addCookie(new Cookie(ZUserServiceImpl.RETURN_TO_KEY, URLEncoder.encode(returnToUrl, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		}
		
		
		return openIdManager.getAuthenticationUrl(googleEndpoint, googleAssociation);
	}

	@Override
	public String checkAuthentification(HttpServletRequest request) {
		if (!checkNonce(request.getParameter("openid.response_nonce"))) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		String rawMacKeyString = null, returnToUrl = null, alias = null;
		int i=0;
		try {
			while (i<cookies.length && (rawMacKeyString == null || returnToUrl == null || alias == null)) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(ZUserServiceImpl.RAW_MAC_COOKIE_KEY)) {
					rawMacKeyString = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} else if (cookie.getName().equals(ZUserServiceImpl.ENDPOINT_ALIAS_KEY)) {
					alias = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} else if (cookie.getName().equals(ZUserServiceImpl.RETURN_TO_KEY)) {
					returnToUrl = URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
				i++;
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		}
		if (rawMacKeyString != null && returnToUrl != null && alias != null) {
			openIdManager.setReturnTo(returnToUrl);
			try {
				Authentication authentication = openIdManager.getAuthentication(request, Base64.decode(rawMacKeyString), alias);
				String token = hashStrings(authentication.getEmail(), authentication.getFullname(), rawMacKeyString);
				serverCache.authenticateNewUser(token, getZPowerFromEmail(authentication.getEmail()));
				LOG.info("The user " + authentication.getEmail() + " just logged in on the website.");
				return token;
			}catch (NoSuchAlgorithmException e) {
				LOG.error(e.getMessage());
			}catch (OpenIdException e) {
				LOG.error(e.getMessage());
			}
		}
		LOG.info("The authentification with Google failed.");
		return null;
	}

	private ZPower getZPowerFromEmail(String email) {
		ZUser zUser = zUserMapper.getZUserWithEmail(email);
		if (zUser == null) {
			zUser = new ZUser();
			zUser.setEmail(email);
			zUser.setZPower(ZPower.USER);
			zUser.setPassword(UUID.randomUUID().toString());
			zUserMapper.addZUser(zUser);
		}
		return zUser.getZPower();
	}
	
	private boolean checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20) {
            LOG.info("The nonce is null or too short.");
        	return false;
        }
        long nonceTime = getNonceTime(nonce);
        long diff = System.currentTimeMillis() - nonceTime;
        if (diff < 0)
            diff = (-diff);
        if (diff > ONE_HOUR) {
            LOG.info("The nonce was more than hour ago.");
            return false;
        } 
        if (serverCache.isNonceAlreadyStored(nonce)) {
            LOG.info("The nonce is already registered in the db.");
            return false;
        }
        serverCache.storeNonceInCache(nonce);
        return true;
    }
	
	private long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch (java.text.ParseException e) {
			LOG.error(e.getMessage());
			return 0;
		}
    }

}