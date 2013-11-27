package com.zenika.zbooks.web;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.persistence.ZBooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/*")
@Controller
public class zBookAPIController {

    @Autowired
    private ZBooksMapper zBooksMapper;

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
    public void updateBook(@RequestBody ZBook book) {
        zBooksMapper.updateBook(book);
    }
}
