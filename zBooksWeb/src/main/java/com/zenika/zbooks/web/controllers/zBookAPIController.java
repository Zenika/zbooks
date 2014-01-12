package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    
    @RequestMapping(value="/canReturnBook/{id}", method=RequestMethod.GET)
    @ResponseBody
    public boolean canReturnBook(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token, @PathVariable int id) {
    	return zUserService.canReturnBook(token, id);
    }
}
