package com.zenika.zbooks.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zenika.zbooks.gwt.client.entity.ZBook;

@RemoteServiceRelativePath("zBookGetter")
public interface ZBookGetterService extends RemoteService {

	ZBook getZBook (int isbn);
}
