package com.zenika.zbooks.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zenika.zbooks.gwt.client.entity.ZBook;


public interface ZBookRpcServiceAsync {

	void getZBook(int isbn, AsyncCallback<ZBook> callback);

	void getAllZBooks(AsyncCallback<List<ZBook>> callback);

}
