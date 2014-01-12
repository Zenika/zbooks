package com.zenika.zbooks.web;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZPower;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.exceptions.InvalidResourceException;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @RequestMapping(value = "/book", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createBook(@RequestBody ZBook book, UriComponentsBuilder builder) {
        if (book == null || !book.isValid()) {
            throw new InvalidResourceException(); // TODO add error description
        }

        zBooksMapper.addBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/book/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody ZBook book) {
        if (book == null || !book.isValid()) {
            throw new InvalidResourceException(); // TODO add error description
        }

        zBooksMapper.updateBook(book);
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
    public boolean returnBook(@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token, @PathVariable int id) {
    	return zUserService.returnBook(token, id);
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
}
