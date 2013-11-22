package com.zenika.zbooks.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;

@RemoteServiceRelativePath("zBookRpc")
public interface ZBookRpcService extends RemoteService {

	ZBook getZBook (long isbn);
	
	List<ZBook> getAllZBooks ();
	
	void addZBook (long isbn, ZenikaCollection collection);
}
