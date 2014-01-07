package com.zenika.zbooks.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;

@RequestMapping(value = "/api/*")
@Controller
public class zBookAPIController {

    @Autowired
    private ZBooksMapper zBooksMapper;
    @Autowired
    private ZUserService zUserService;

    @RequestMapping(value = "/book", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List list() {
        return zBooksMapper.getBooks();
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ZBook getBook(@PathVariable int id) {
        return zBooksMapper.getBook(id);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteBook(@PathVariable int id) {
        zBooksMapper.deleteBook(id);
    }


    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ZBook updateBook(@RequestBody ZBook book) {
        if (book.getId() == 0)
            zBooksMapper.addBook(book);
        else
            zBooksMapper.updateBook(book);

        return book;
    }

    @RequestMapping(value = "/hasSpecialAccess", method = RequestMethod.GET)
    @ResponseBody
    public boolean hasSpecialAccess(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        return (zUserService.getZUserAccess(token) == ZPower.ADMIN);
    }
    
    @RequestMapping(value = "/getBorrower/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getBorrower(@PathVariable int id) {
        return (zBooksMapper.getBook(id).getBorrowerName());
    }
    
    @RequestMapping(value="/borrow/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public boolean borrowBook(@PathVariable int id, @CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
    	ZBook zBook = zBooksMapper.getBook(id);
    	ZUser user = zUserService.getAuthenticatedZUser(token);
    	return zUserService.borrowBook(user, zBook);
    }
    
    @RequestMapping(value="/return/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public boolean returnBook(@PathVariable int id) {
    	return zUserService.returnBook(id);
    }
    
    @RequestMapping(value="/getFirstName", method=RequestMethod.GET)
    @ResponseBody
    public String getFirstName(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
    	return zUserService.getUserFirstName(token);
    }
}
