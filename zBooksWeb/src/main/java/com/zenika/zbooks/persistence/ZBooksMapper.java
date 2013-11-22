package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZBooksMapper {
	
	void addBook (ZBook book);
	
	void deleteBook (int id);

	ZBook getBook (int id);

	List<ZBook> getBooks ();

}
