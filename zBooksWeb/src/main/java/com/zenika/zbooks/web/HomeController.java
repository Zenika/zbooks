package com.zenika.zbooks.web;

import com.zenika.zbooks.entity.Language;
import com.zenika.zbooks.entity.ZBook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class HomeController {

	@RequestMapping(value="/", method=RequestMethod.GET, produces = "text/html")
	public String index() {
		return "index.html";
	}

    @RequestMapping(value="/", method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ArrayList list () {
        ArrayList<ZBook> listBooks = new ArrayList<>();
        ZBook book = new ZBook();
        book.setISBN("047094224X");
        book.setTitle("Professional NoSQL John Wiley & Sons Ltd");
        book.setLanguage(Language.EN);
        listBooks.add(book);

        book = new ZBook();
        book.setISBN("0596518846X");
        book.setTitle("SQL in a Nutshell\tO'Reilly");
        book.setLanguage(Language.EN);
        listBooks.add(book);

        book = new ZBook();
        book.setISBN("2212136382X");
        book.setTitle("HTML 5 : Une référence pour le développeur web\tEyrolles");
        book.setLanguage(Language.FR);
        listBooks.add(book);


        book = new ZBook();
        book.setISBN("2744025828X");
        book.setTitle("Javascript - Les bons éléments");
        book.setLanguage(Language.FR);
        listBooks.add(book);
        return listBooks;
    }

	@RequestMapping(value="/book/{id}", method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
	public ZBook getBook (@PathVariable String id) {
        ZBook book = new ZBook();
        book.setISBN("isbn_"+id);
        book.setTitle("Professional NoSQL John Wiley & Sons Ltd");
        book.setLanguage(Language.EN);
        return book;
    }

}