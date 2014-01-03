package com.zenika.zbooks.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;

@Controller
public class HomeController {

	@Autowired
	private ZUserService zUserService;
	
	private static final Logger logger = Logger.getLogger(HomeController.class);
	private String serverUrl;
	private boolean isSecureWorking = true;
	
	public void setIsSecureWorking (boolean isSecureWorking) {
		this.isSecureWorking = isSecureWorking;
	}
	
	public void setServerUrl (String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@RequestMapping(value="/", produces = MediaType.TEXT_HTML_VALUE)
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isSecureWorking && !request.isSecure()) {
			response.sendRedirect(serverUrl);
		}
        return "index.html";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public boolean logIn (@RequestBody ZUser user, HttpServletResponse response) {
		String token = zUserService.connectZUser(user);
		if (token != null) {
			response.addCookie(new Cookie(ZBooksUtils.COOKIE_TOKEN_KEY, token));
			return true;
		}
		return false;
	}
	
    @RequestMapping(value="/logInWithGoogle")
    public void logInWithGoogle (HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String token = ZBooksUtils.getCookieValue(request.getCookies(), ZBooksUtils.COOKIE_TOKEN_KEY);
    	if (token != null && zUserService.isZUserAuthenticated(token)) {
    		response.sendRedirect("/#/list");
    	} else {
	    	String googleRequestURL = zUserService.connectUserWithGoogle("identifiedWithGoogle", request, response);
	    	response.sendRedirect(googleRequestURL);
    	}
    }
    
    @RequestMapping(value="/identifiedWithGoogle")
    public void checkIdentity (HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String token = zUserService.checkAuthentification(request);
    	if (token != null) {
    		response.addCookie(new Cookie(ZBooksUtils.COOKIE_TOKEN_KEY, token));
    		response.sendRedirect(serverUrl+"#/list");
    	} else {
    		response.sendRedirect("/");
    	}
    }
    
    @RequestMapping(value="/authenticated")
    @ResponseBody
    public boolean isAuthenticated (@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
    	logger.debug("The zUser tried to see if he was connected. Response was : " + zUserService.isZUserAuthenticated(token));
    	return zUserService.isZUserAuthenticated(token);
    }
	
    @RequestMapping(value="/disconnect", method=RequestMethod.PUT)
    public void disconnectZUser (@CookieValue(value=ZBooksUtils.COOKIE_TOKEN_KEY, required=false) String token) {
    	zUserService.disconnectZUser(token);
    }
}