package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.Activity;
import com.zenika.zbooks.entity.ActivityType;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZBookOfTheMonth;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.exceptions.InvalidResourceException;
import com.zenika.zbooks.persistence.ActivityMapper;
import com.zenika.zbooks.persistence.ZBookOfTheMonthMapper;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import com.zenika.zbooks.web.resources.Books;
import com.zenika.zbooks.web.resources.util.Link;

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

import java.util.Date;
import java.util.List;

@RequestMapping(value = "/api/botm")
@Controller
public class BookOfTheMonthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOfTheMonthController.class);
    private static final int MAX_ELEMENT_A_PAGE = 5;

    @Autowired
    private ZBooksMapper zBooksMapper;

    @Autowired
    private ZBookOfTheMonthMapper zBookOfTheMonthMapper;

    @Autowired
    private ZUserService zUserService;

    @Autowired
    private ActivityMapper activityMapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ZBookOfTheMonth> list(UriComponentsBuilder uriBuilder,
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam(defaultValue = "5") int nbResults,
            @RequestParam(defaultValue = "ASC") String order) {
        return zBookOfTheMonthMapper.getAllBooksOfTheMonth();

    }

    @RequestMapping(value="/{id}/vote",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void vote(@RequestParam int bookId,
    		@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token){
        LOGGER.info("A vote for a BotM is being recorded");

    	ZUser voter = zUserService.getAuthenticatedZUser(token);
    	ZBookOfTheMonth zBookOfTheMonth = zBookOfTheMonthMapper.getBookOfTheMonthById(bookId);
    	zBookOfTheMonthMapper.vote(voter, zBookOfTheMonth);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity proposeBookOfTheMonth(
    		@RequestBody ZBook book,
    		UriComponentsBuilder builder,
    		@CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        LOGGER.info("A BotM is being proposed");
        if (book == null || !book.isValid()) {
            throw new InvalidResourceException();
        }

        ZUser user = zUserService.getAuthenticatedZUser(token);

        zBookOfTheMonthMapper.addBookOfTheMonth(user, book);



        Activity addBook = new Activity();
        addBook.setDate(new Date());
        addBook.setType(ActivityType.BOOK_OF_THE_MONTH);
        addBook.setUser(user);

        activityMapper.addActivity(addBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/books/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


}
