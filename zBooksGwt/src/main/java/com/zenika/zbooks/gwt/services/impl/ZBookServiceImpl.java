package com.zenika.zbooks.gwt.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;
import com.zenika.zbooks.gwt.dao.AuthorRepository;
import com.zenika.zbooks.gwt.dao.ZBookRepository;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service
@Transactional
public class ZBookServiceImpl implements ZBookService {

	private static final Logger LOG = Logger.getLogger(ZBookServiceImpl.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private ZBookRepository zBookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public ZBook createOrUpdate(ZBook zBook) {
		List<Author> authors = zBook.getAuthors();
		for (int i=0; i<authors.size(); i++) {
			Author author = authors.get(i);
			try {
				Author authorInDB = authorRepository.findByName(author.getFirstName(), author.getLastName());
				author.setId(authorInDB.getId());
			} catch (NoResultException e) {
				authorRepository.save(author);
			}
		}
		return zBookRepository.save(zBook);
	}

	@Override
	public void delete(long isbn) {
		ZBook zBookToDelete = this.findByIsbn(isbn);
		zBookRepository.delete(zBookToDelete);
	}

	@Override
	public List<ZBook> findAll() {
		return zBookRepository.findAll();
	}

	@Override
	public ZBook findByIsbn(long isbn) {
		return zBookRepository.findByIsbn(isbn);
	}

	@Override
	public void deleteAll() {
		List<ZBook> zBooksList = zBookRepository.findAll();
		for (ZBook zBook : zBooksList) {
			zBookRepository.delete(zBook);
		}
	}

	@Override
	public ZBook createOrUpdate(long isbn, ZenikaCollection collection) {
		ZBook zBook = new ZBook();
		zBook.setISBN(isbn);
		zBook.setCollection(collection);
		String result = restTemplate.getForObject("http://isbndb.com/api/v2/json/LHEB8EED/books?q={isbn}", String.class, isbn);
		LOG.info("Requesting the ZBook : " + isbn + " in isbndb. Result : " + result);
		if (result != null) {
			try {
				JSONObject json = new JSONObject(result);
				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject bookJSon = jsonArray.getJSONObject(0);
				return this.fillZBookDataFromJSON (zBook, bookJSon);
			} catch (JSONException e) {
				LOG.error("A JSONException happened while requesting the isbndb.com website : " + e.getMessage());
			}
		}
		return null;
	}
	
	private ZBook fillZBookDataFromJSON(ZBook zBook, JSONObject json) {
		try {
			//Contains the title
			String title = json.getString("title_latin");
			//Contains the edition + the release date separated by a '; '
			String editionAndReleaseDate = json.getString("edition_info");
			String edition = editionAndReleaseDate.split("; ")[0];
			//Contains the amount of pages with the form "XXX pages"
			String physicalDescription = json.getString("physical_description_text");
			if (physicalDescription.split(" ")[0].matches("[0-9]*")) {
				int pagesNumber = Integer.parseInt(physicalDescription.split(" ")[0]);
				zBook.setPagesNumber(pagesNumber);
			}
			JSONArray authorsJSON = json.getJSONArray("author_data");
			List<Author> authors = new ArrayList<Author>();
			for (int i=0; i<authorsJSON.length(); i++) {
				String authorString = authorsJSON.getJSONObject(i).getString("name");
				String[] authorsArrayString = authorString.split(" ");
				Author author = new Author();
				author.setFirstName(authorsArrayString[0]);
				author.setLastName(authorsArrayString[1]);
				authors.add(author);
			}
			zBook.setAuthors(authors);
			zBook.setTitle(title);
			zBook.setEdition(edition);
			return this.createOrUpdate(zBook);
		} catch (JSONException e) {
			LOG.error("A JSONException happened while filling the data of the ZBook from the JSON : " + e.getMessage());
		}
		return null;
	}
	
	void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
