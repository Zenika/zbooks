package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ZUserService zUserService;
	
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

	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity logIn (@RequestBody ZUser user, HttpServletResponse response, UriComponentsBuilder builder) {
        ZUser authenticatedUser = zUserService.authenticateZUser(user);
        if (authenticatedUser != null) {
            String token = zUserService.createToken(authenticatedUser);
            if (token != null) {
                response.addCookie(new Cookie(ZBooksUtils.COOKIE_TOKEN_KEY, token));

                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/api/users/{id}").buildAndExpand(authenticatedUser.getId()).toUri());

                return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
            }
        }

		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
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
    	LOGGER.debug("The zUser tried to see if he was connected. Response was : {}", zUserService.isZUserAuthenticated(token));
    	return zUserService.isZUserAuthenticated(token);
    }
	
    @RequestMapping(value="/disconnect", method=RequestMethod.PUT)
    @ResponseBody
    public void disconnectZUser (@CookieValue(value=ZBooksUtils.COOKIE_TOKEN_KEY, required=false) String token) {
    	zUserService.disconnectZUser(token);
    }
}