package com.zenika.zbooks.gwt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.zbooks.gwt.dao.ZBookRepository;
import com.zenika.zbooks.gwt.entity.ZBook;
import com.zenika.zbooks.gwt.services.ZBookService;

@Service
@Transactional
public class ZBookServiceImpl implements ZBookService {

	@Autowired
	private ZBookRepository zBookRepository;
	
	@Override
	public ZBook create(ZBook zBook) {
		return zBookRepository.save(zBook);
	}

	@Override
	public void delete(int isbn) {
		zBookRepository.delete(isbn);
	}

	@Override
	public List<ZBook> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZBook update(ZBook zBook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZBook findByIsbn(int isbn) {
		return zBookRepository.findOne(isbn);
	}

}
