package com.zenika.zbooks.web;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.persistence.ZBooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping(value="/api/*")
@Controller
public class zBookController {

    @Autowired
    private ZBooksMapper zBooksMapper;

    @RequestMapping(value="/book", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List list () {
        return zBooksMapper.getBooks();
    }

    @RequestMapping(value="/book/{id}", method= RequestMethod.GET)
    @ResponseBody
    public ZBook getBook (@PathVariable int id) {
        return zBooksMapper.getBook(id);
    }
}
