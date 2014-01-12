package com.zenika.zbooks.web.interceptors;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SpecialPowerAuthentificationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(SpecialPowerAuthentificationInterceptor.class);
	
	@Autowired
	private ZUserService zUserService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		if (!request.getMethod().equals("GET")) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				String token = ZBooksUtils.getCookieValue(cookies, ZBooksUtils.COOKIE_TOKEN_KEY);
	    		if (token != null && !zUserService.isZUserAuthenticated(token)) {
					logger.info("Someone tried to access your API but didn't give any username");
				} else if (token != null) {
					if (zUserService.getZUserAccess(token) == ZPower.ADMIN) {
						logger.info("An admin is accessing a reserved zone.");
						return true;
					} else {
						logger.info("A user tried to access a forbidden zone");
					}
				}
	    	}
	    	response.sendError(403, "Vous n'êtes pas authorisé à accéder à cette page.");
	    	return false;
		} else {
			return true;
		}
	}
}
