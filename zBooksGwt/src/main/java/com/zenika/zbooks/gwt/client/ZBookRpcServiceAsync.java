package com.zenika.zbooks.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;


public interface ZBookRpcServiceAsync {

	void getZBook(long isbn, AsyncCallback<ZBook> callback);

	void getAllZBooks(AsyncCallback<List<ZBook>> callback);

	void addZBook(long isbn, ZenikaCollection collection,
			AsyncCallback<ZBook> callback);

}
