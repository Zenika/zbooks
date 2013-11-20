package com.zenika.zbooks.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zenika.zbooks.gwt.client.entity.ZBook;

@RemoteServiceRelativePath("zBookRpc")
public interface ZBookRpcService extends RemoteService {

	ZBook getZBook (int isbn);
	
	List<ZBook> getAllZBooks ();
}
