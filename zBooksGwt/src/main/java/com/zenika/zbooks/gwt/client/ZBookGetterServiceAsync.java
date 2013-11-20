package com.zenika.zbooks.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zenika.zbooks.gwt.client.entity.ZBook;


public interface ZBookGetterServiceAsync {

	void getZBook(int isbn, AsyncCallback<ZBook> callback);

}
