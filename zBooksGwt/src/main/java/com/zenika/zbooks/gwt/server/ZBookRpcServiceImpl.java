package com.zenika.zbooks.gwt.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.zbooks.gwt.client.ZBookRpcService;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service("zBookRpcService")
public class ZBookRpcServiceImpl implements ZBookRpcService {

	@Autowired
	private ZBookService zBookService;

	/**
	 * 
	 */

	public ZBook getZBook(int isbn) {
		return this.zBookService.findByIsbn(isbn);
	}

	@Override
	public List<ZBook> getAllZBooks() {
		return this.zBookService.findAll();
	}
}
