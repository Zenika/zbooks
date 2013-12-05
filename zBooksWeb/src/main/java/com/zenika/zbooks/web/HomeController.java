package com.zenika.zbooks.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;

@Controller
public class HomeController {

	@Autowired
	private ZUserService zUserService;
	
	@RequestMapping(value="/", produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
        return "index.html";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = "application/json")
	public void loggIn (@RequestBody ZUser user, HttpServletResponse response) {
		String token = zUserService.connectZUser(user);
		if (token != null) {
			response.addCookie(new Cookie(ZBooksUtils.COOKIE_TOKEN_KEY, token));
		}
	}
	
    @RequestMapping(value="/logInWithGoogle")
    public void logInWithGoogle (HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String token = ZBooksUtils.getCookieValue(request.getCookies(), ZBooksUtils.COOKIE_TOKEN_KEY);
    	if (token != null && zUserService.isZUserAuthenticated(token)) {
    		response.sendRedirect("/#/list");
    	} else {
	    	String googleRequestURL = zUserService.connectUserWithGoogle("http://localhost:8080/identifiedWithGoogle", request, response);
	    	response.sendRedirect(googleRequestURL);
    	}
    }
    
    @RequestMapping(value="/identifiedWithGoogle")
    public void checkIdentity (HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String token = zUserService.checkAuthentification(request);
    	if (token != null) {
    		response.addCookie(new Cookie(ZBooksUtils.COOKIE_TOKEN_KEY, token));
    		response.sendRedirect("https://localhost:8443/#/list");
    	} else {
    		response.sendRedirect("/");
    	}
    }
	
}