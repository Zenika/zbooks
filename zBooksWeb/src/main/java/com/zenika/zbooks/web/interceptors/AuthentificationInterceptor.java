package com.zenika.zbooks.web.interceptors;

import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthentificationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationInterceptor.class);

	@Autowired
	private ZUserService zUserService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		Cookie[] cookies = request.getCookies();
    	if (cookies != null) {
	    	
    		String token = ZBooksUtils.getCookieValue(cookies, ZBooksUtils.COOKIE_TOKEN_KEY);
	    			
			if (token != null && !zUserService.isZUserAuthenticated(token)) {
                LOGGER.info("Someone tried to access your API but the token {} isn't registered.", token);
			} else if (token != null) {
                LOGGER.info("A user is accessing your API.");
				return true;
			}
    	}
    	response.sendError(401, "Vous devez vous authentifier pour accéder à cette page.");
		
		
		return false;
	}
}
