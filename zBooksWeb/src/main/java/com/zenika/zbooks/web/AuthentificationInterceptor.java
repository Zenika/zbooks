package com.zenika.zbooks.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zenika.zbooks.services.ZUserService;

@Component
public class AuthentificationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(AuthentificationInterceptor.class);
	
	@Autowired
	private ZUserService zUserService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		Cookie[] cookies = request.getCookies();
		String userName = null, token = null;
    	if (cookies != null) {
	    	
	    	int i=0;
	    	while (i<cookies.length && (userName == null || token == null)) {
	    		Cookie cookie = cookies[i];
	    		if (cookie.getName().equals("userName")) {
	    			userName = cookie.getValue();
	    		} else if (cookie.getName().equals("token")) {
	    			token = cookie.getValue();
	    		}
	    		i++;
	    	}
		
			if (!(userName != null && token != null && zUserService.isZUserAuthenticated(userName, token))) {
				if (userName != null) {
					logger.info("The user " + userName + " failed to access your API.");
				} else {
					logger.info("Someone tried to access your API but didn't give any username");
				}
			} else {
				logger.info("The user " + userName + " is accessing your API.");
				return true;
			}
    	}
    	response.sendError(403, "Vous n'êtes pas authorisé à accéder à cette page.");
		
		
		return false;
	}
}
