package com.zenika.zbooks.services.impl;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ServerCache;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.persistence.ZUserMapper;
import com.zenika.zbooks.services.ZUserService;
import org.apache.log4j.Logger;
import org.expressme.openid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ZUserServiceImpl implements ZUserService {

	@Autowired
	private ZUserMapper zUserMapper;
	
	@Autowired
	private ZBooksMapper zBooksMapper;
	
	private static final Logger LOG = Logger.getLogger(ZUserServiceImpl.class);
	
	@Autowired
	private ServerCache serverCache;
	
	private OpenIdManager openIdManager = new OpenIdManager();
	
	private static final String RAW_MAC_COOKIE_KEY = "RawMacKey";
	private static final String ENDPOINT_ALIAS_KEY = "Alias";
	private static final String RETURN_TO_KEY = "ReturnTo";
	private static final long ONE_HOUR = 3600000L;
	private String serverUrl;
	public static final String ZNK_EMAIL_PATTERN = ".*@zenika.com";
	
	
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

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
        zUserMapper.deleteZUserProfile(userInDb.getId());
        zUserMapper.deleteZUser(userInDb.getId());
	}

	@Override
	public boolean isZUserAuthenticated (String token) {
		return serverCache.isUserAuthenticated(token);
	}

    @Override
    public ZUser authenticateZUser(ZUser user) {
        return zUserMapper.getZUser(user.getEmail(), user.getPassword());
    }

    @Override
    public String createToken(ZUser user) {
            try {
                String token = this.hashZUser(user);
                user.setPassword(null);
                serverCache.authenticateNewUser(token, user);
                return token;
            } catch (NoSuchAlgorithmException e) {
                LOG.error(e.getMessage());
                return null;
            }
    }

	@Override
    @Deprecated
	public String connectZUser(ZUser user) {
		ZUser userInDb = zUserMapper.getZUser(user.getEmail(), user.getPassword());
		if (userInDb != null) {
			try {
				String token = this.hashZUser(user);
				userInDb.setPassword(null);
				serverCache.authenticateNewUser(token, userInDb);
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
		
		//Using the date to be sure it'll never be the same token
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSSS");
		String date = dateFormat.format(new Date());
		
		return hashStrings(email, password, date);
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
		openIdManager.setReturnTo(serverUrl + returnToUrl);
		openIdManager.setRealm(serverUrl);
		
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
			openIdManager.setReturnTo(serverUrl + returnToUrl);
			try {
				Authentication authentication = openIdManager.getAuthentication(request, Base64.decode(rawMacKeyString), alias);
				if (authentication.getEmail().matches(ZNK_EMAIL_PATTERN)) {
					String token = hashStrings(authentication.getEmail(), authentication.getFullname(), rawMacKeyString);
					serverCache.authenticateNewUser(token, getZUserFromEmail(authentication));
					LOG.info("The user " + authentication.getEmail() + " just logged in on the website.");
					return token;
				}
			}catch (NoSuchAlgorithmException e) {
				LOG.error(e.getMessage());
			}catch (OpenIdException e) {
				LOG.error(e.getMessage());
			}
		}
		LOG.info("The authentification with Google failed.");
		return null;
	}

	private ZUser getZUserFromEmail(Authentication authentication) {
		ZUser zUser = zUserMapper.getZUserWithEmail(authentication.getEmail());
		if (zUser == null) {
			zUser = new ZUser();
			zUser.setEmail(authentication.getEmail());
			zUser.setUserName(authentication.getFullname());
			zUser.setZPower(ZPower.USER);
			zUser.setPassword(UUID.randomUUID().toString());
			zUserMapper.addZUser(zUser);
			zUser.setPassword(null);
		}
		return zUser;
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

	@Override
	public ZUser getAuthenticatedZUser(String token) {
		return serverCache.getZUser(token);
	}

	@Override
	public boolean borrowBook(ZUser zUser, ZBook zBook) {
		if (zBook != null && zUser != null) {
            zBooksMapper.borrowBook(zBook, zUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean returnBook(String token, int book_id) {
		if (canReturnBook(token, book_id)) {
			ZBook zBook = zBooksMapper.getBook(book_id);
			if (zBook != null) {
                zBooksMapper.returnBook(zBook);
				return true;
			}
		}
		return false;
	}

	@Override
	public void disconnectZUser(String token) {
		if (token != null && serverCache.isUserAuthenticated(token)) {
			serverCache.disconnectZUser(token);
		}
	}

	@Override
	public String getUserFirstName(String token) {
		ZUser zUser = serverCache.getZUser(token);
		String[] names = zUser.getUserName().split(" ");
		return names[0];
	}

	@Override
	public boolean canReturnBook(String token, int idZBook) {
		boolean hasBorrowed = false;
		ZUser user = serverCache.getZUser(token);
		if (user.getZPower() == ZPower.ADMIN) {
			hasBorrowed = true;
		} else {
            hasBorrowed = zUserMapper.hasBorrowedBook(user.getId(), idZBook);
		}
		return hasBorrowed;
	}

    @Override
    public ZUser getUser(int id) {
        return zUserMapper.getUserWithProfile(id);
    }

}
