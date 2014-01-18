package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/old")
@Controller
public class zBookAPIController {

    @Autowired
    private ZUserService zUserService;

    @RequestMapping(value="/getFirstName", method=RequestMethod.GET)
    @ResponseBody
    public String getFirstName(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
    	return zUserService.getUserFirstName(token);
    }
    
}
