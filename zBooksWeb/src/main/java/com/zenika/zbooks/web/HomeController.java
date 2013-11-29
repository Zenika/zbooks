package com.zenika.zbooks.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.services.ZUserService;

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
			String userName = user.getUserName();
			response.addCookie(new Cookie("userName", userName));
			response.addCookie(new Cookie("token", token));
		}
	}
	
}