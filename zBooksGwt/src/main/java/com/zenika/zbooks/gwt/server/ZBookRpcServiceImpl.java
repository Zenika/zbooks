package com.zenika.zbooks.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.gwt.client.ZBookRpcService;
import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service("zBookRpcService")
public class ZBookRpcServiceImpl implements ZBookRpcService {

	private static final Logger LOG = Logger.getLogger(ZBookRpcServiceImpl.class);
	
	@Autowired
	private ZBookService zBookService;

	@Override
	public ZBook getZBook(long isbn) {
		LOG.info("RPC query : getZBook with the argument " + isbn);
		return this.zBookService.findByIsbn(isbn);
	}

	@Override
	public List<ZBook> getAllZBooks() {
		LOG.info("RPC query : getAllZBooks");
		this.fillDB();
		List<ZBook> zBooksList = this.zBookService.findAll();
		List<ZBook> zBookListToReturn = new ArrayList<ZBook>();
		for (ZBook zBook : zBooksList) {
			zBookListToReturn.add(this.makeUnpersistent(zBook));
		}
		
		return zBookListToReturn;
	}
	
	private ZBook makeUnpersistent(ZBook zBook) {
		ArrayList<Author> authors = new ArrayList<Author>();
		for (Author author : zBook.getAuthors()) {
			authors.add(this.makeUnpersistent(author));
		}
		ZBook zBookToReturn = new ZBook(zBook.getISBN(), zBook.getEdition(), zBook.getTitle(), zBook.getPagesNumber(), authors);
		zBookToReturn.setLanguage(zBook.getLanguage());
		zBookToReturn.setCollection(zBook.getCollection());
		return zBookToReturn;
	}

	private Author makeUnpersistent(Author author) {
		return new Author(author.getId(), author.getFirstName(), author.getLastName());
	}

	private void fillDB() {
		ZBook zBook1 = new ZBook();
		Author author1 = new Author();
		author1.setFirstName("Pierre");
		author1.setLastName("Dupont");
		zBook1.setISBN(42);
		zBook1.setTitle("Le meilleur livre de Zenika");
		zBook1.setEdition("Zenika");
		zBook1.setPagesNumber(250);
		
		ZBook zBook2 = new ZBook();
		zBook2.setISBN(65);
		zBook2.setTitle("Sans titre");
		zBook2.setEdition("Ni d'édition");
		zBook2.setPagesNumber(30);
		
		Author author2 = new Author();
		author2.setFirstName("John");
		author2.setLastName("Doe");
		
		ArrayList<Author> authorsZBook1 = new ArrayList<Author>();
		authorsZBook1.add(author1);
		authorsZBook1.add(author2);
		zBook1.setAuthors(authorsZBook1);
		
		ArrayList<Author> authorsZBook2 = new ArrayList<Author>();
		authorsZBook2.add(author1);
		zBook2.setAuthors(authorsZBook2);
		
		this.zBookService.createOrUpdate(zBook1);
		this.zBookService.createOrUpdate(zBook2);
	}

	@Override
	public void addZBook(long isbn, ZenikaCollection collection) {
		LOG.info("Adding a ZBook with the ISBN : " + isbn + " in the collection " + collection);
		this.zBookService.createOrUpdate(isbn, collection);
	}

	
}
