package com.zenika.zbooks.gwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenika.zbooks.gwt.client.entity.ZBook;

public interface ZBookRepository extends JpaRepository<ZBook, Integer>{

}
