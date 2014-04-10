package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZBookOfTheMonth;
import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZBookOfTheMonthMapper {
	List<ZBookOfTheMonth> getAllBooksOfTheMonth();

	void vote(
		@Param("voter") ZUser voter, 
		@Param("book") ZBookOfTheMonth book);

	ZBookOfTheMonth addBookOfTheMonth(
		@Param("submitter") ZUser submitter, 
		@Param("book") ZBook book);

}