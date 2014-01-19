package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZUser;
import com.zenika.zbooks.exceptions.InvalidResourceException;
import com.zenika.zbooks.persistence.ZBooksMapper;
import com.zenika.zbooks.services.ZUserService;
import com.zenika.zbooks.utils.ZBooksUtils;
import com.zenika.zbooks.web.resources.Books;
import com.zenika.zbooks.web.resources.util.Link;
import com.zenika.zbooks.web.resources.util.Links;
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

@RequestMapping(value = "/api/books")
@Controller
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private static final int MAX_ELEMENT_A_PAGE = 5;

    @Autowired
    private ZBooksMapper zBooksMapper;
    @Autowired
    private ZUserService zUserService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Books list(UriComponentsBuilder uriBuilder, @RequestParam(defaultValue = "0") int page) {
        Books books = new Books();
        books.setBooks(zBooksMapper.getBooks());

        int maxNumberOfBooks = zBooksMapper.getNumberOfBooks();

        int numberOfPages = maxNumberOfBooks / MAX_ELEMENT_A_PAGE;
        numberOfPages = (maxNumberOfBooks % MAX_ELEMENT_A_PAGE == 0) ? numberOfPages : numberOfPages+1;
        numberOfPages = (numberOfPages == 0) ? 1 : numberOfPages;
        books.setNumberOfPages(numberOfPages);

        LOGGER.debug("Liste des livres de la page {} : {}", page, books);
        LOGGER.trace("Nombre max de livres : {}", maxNumberOfBooks);

        Links links = new Links();
        links.addLink(new Link("current", uriBuilder.path("/api/books?page="+page).build().toUriString()));

        if (page<numberOfPages) {
            links.addLink(new Link("next", uriBuilder.path("/api/books?page="+(page+1)).build().toUriString()));
        }

        if (page>1) {
           links.addLink(new Link("previous", uriBuilder.path("/api/books?page="+(page-1)).build().toUriString()));
        }

        books.setLinks(links);
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
    public ResponseEntity createBook(@RequestBody ZBook book, UriComponentsBuilder builder) {
        if (book == null || !book.isValid()) {
            throw new InvalidResourceException(); // TODO add error description
        }

        zBooksMapper.addBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/books/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody ZBook book) {
        if (book == null || !book.isValid()) {
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
