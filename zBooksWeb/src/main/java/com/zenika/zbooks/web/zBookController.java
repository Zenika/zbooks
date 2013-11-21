package com.zenika.zbooks.web;

import com.zenika.zbooks.entity.ZBook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@RequestMapping(value="/api/*")
@Controller
public class zBookController {

    @RequestMapping(value="/book", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ArrayList> list () {
        ArrayList<ZBook> listBooks = new ArrayList<>();
        ZBook book = new ZBook();
        book.setISBN("047094224X");
        book.setTitle("Professional NoSQL John Wiley & Sons Ltd");
        book.setLanguage("EN");
        listBooks.add(book);

        book = new ZBook();
        book.setISBN("0596518846X");
        book.setTitle("SQL in a Nutshell\tO'Reilly");
        book.setLanguage("EN");
        listBooks.add(book);

        book = new ZBook();
        book.setISBN("2212136382X");
        book.setTitle("HTML 5 : Une référence pour le développeur web\tEyrolles");
        book.setLanguage("FR");
        listBooks.add(book);


        book = new ZBook();
        book.setISBN("2744025828X");
        book.setTitle("Javascript - Les bons éléments");
        book.setLanguage("FR");
        listBooks.add(book);


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Vary", "Accept");
        return new ResponseEntity<ArrayList>(listBooks, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value="/book/{id}", method= RequestMethod.GET)
    @ResponseBody
    public ZBook getBook (@PathVariable String id) {
        ZBook book = new ZBook();
        book.setISBN("isbn_"+id);
        book.setTitle("Professional NoSQL John Wiley & Sons Ltd");
        return book;
    }
}
