package com.zenika.zbooks.gwt.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.gwt.client.ZBookGetterService;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service("zBookGetterService")
public class ZBookGetterServiceImpl implements ZBookGetterService {

	@Autowired
	private ZBookService zBookService;

	/**
	 * 
	 */

	public ZBook getZBook(int isbn) {
		this.createZBook(isbn);
		return this.zBookService.findByIsbn(isbn);
	}

	public void createZBook (int isbn) {
		//TODO to delete, used only to put some data in the DB for the first test
		ZBook zBook = new ZBook();
		zBook.setISBN(isbn);
		zBook.setTitle("Pas de titre");
		zBook.setEdition("Pas d'édition");
		zBook.setPagesNumber(0);
		this.zBookService.create(zBook);
	}
	
}
