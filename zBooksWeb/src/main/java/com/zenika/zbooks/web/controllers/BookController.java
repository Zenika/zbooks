package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.Activity;
import com.zenika.zbooks.entity.ActivityType;
import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.exceptions.InvalidResourceException;
import com.zenika.zbooks.persistence.ActivityMapper;
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

@RequestMapping(value = "/api/books")
@Controller
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private static final int MAX_ELEMENT_A_PAGE = 5;

    @Autowired
    private ZBooksMapper zBooksMapper;
    @Autowired
    private ZUserService zUserService;

    @Autowired
    private ActivityMapper activityMapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Books list(UriComponentsBuilder uriBuilder, @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "") String sortBy,  @RequestParam(defaultValue = "5") int nbResults,
                      @RequestParam(defaultValue = "ASC") String order) {
        sortBy = sortBy.equalsIgnoreCase("ASC")?"ASC":"DESC";
        order = sortBy.equalsIgnoreCase("DESC")?"ASC":"DESC";

        Books books = new Books();
        books.setBooks(zBooksMapper.getBooksOfPage(page*MAX_ELEMENT_A_PAGE,MAX_ELEMENT_A_PAGE, sortBy, order));

        int maxNumberOfBooks = zBooksMapper.getNumberOfBooks();

        int numberOfPages = maxNumberOfBooks / MAX_ELEMENT_A_PAGE;
        numberOfPages = (maxNumberOfBooks % MAX_ELEMENT_A_PAGE == 0) ? numberOfPages : numberOfPages+1;
        numberOfPages = (numberOfPages == 0) ? 1 : numberOfPages;
        books.setNumberOfPages(numberOfPages);

        LOGGER.debug("Liste des livres de la page {} : {}", page, books);
        LOGGER.trace("Nombre max de livres : {}", maxNumberOfBooks);

        books.addLink(new Link("self", uriBuilder.path("/api/books?page="+page).build().toUriString()));

        if (page<numberOfPages) {
            books.addLink(new Link("next", uriBuilder.path("/api/books?page="+(page+1)).build().toUriString()));
        }

        if (page>1) {
            books.addLink(new Link("previous", uriBuilder.path("/api/books?page="+(page-1)).build().toUriString()));
        }

        return books;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ZBook getBook(@PathVariable int id) {
        return zBooksMapper.getBook(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {
        zBooksMapper.deleteBook(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBook(@RequestBody ZBook book, UriComponentsBuilder builder, @CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        if (book == null || !book.isValid()) {
            throw new InvalidResourceException(); // TODO add error description
        }

        zBooksMapper.addBook(book);

        ZUser user = zUserService.getAuthenticatedZUser(token);

        Activity addBook = new Activity();
        addBook.setDate(new Date());
        addBook.setType(ActivityType.BOOK);
        addBook.setUser(user);

        activityMapper.addActivity(addBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/books/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody ZBook book) {
        if (book == null || !book.isValid()) {
            LOGGER.info("Modification invalide : {}", book);
            throw new InvalidResourceException(); // TODO add error description
        }

        zBooksMapper.updateBook(book);
    }

    @RequestMapping(value="/{id}/borrow", method = RequestMethod.PUT)
    @ResponseBody
    public boolean borrowBook(@PathVariable int id, @CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        ZBook zBook = zBooksMapper.getBook(id);
        ZUser user = zUserService.getAuthenticatedZUser(token);
        boolean b = zUserService.borrowBook(user, zBook);
        LOGGER.debug("Zbooks : {}", zBook);
        return b;
    }

    @RequestMapping(value="/{id}/return", method = RequestMethod.PUT)
    @ResponseBody
    public boolean returnBook(@PathVariable int id, @CookieValue(ZBooksUtils.COOKIE_TOKEN_KEY) String token) {
        return zUserService.returnBook(token, id);
    }

}
