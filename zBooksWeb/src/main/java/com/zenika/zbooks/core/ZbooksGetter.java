package com.zenika.zbooks.core;

import com.zenika.zbooks.entity.ZBook;

public interface ZbooksGetter {

	ZBook getBookFromISBN (String isbn);
}
