package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/users")
@Controller
public class UserController {

    @Autowired
    private ZUserService zUserService;


    @RequestMapping(value = "/hasSpecialAccess", method = RequestMethod.GET)
    @ResponseBody
    public boolean hasSpecialAccess(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        return (zUserService.getZUserAccess(token) == ZPower.ADMIN);
    }

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ZUser getBook(@PathVariable int id) {
        return zUserService.getUser(id);
    }

}
