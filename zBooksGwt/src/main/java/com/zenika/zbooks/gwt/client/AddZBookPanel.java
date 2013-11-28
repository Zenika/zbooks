package com.zenika.zbooks.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.zenika.zbooks.gwt.client.entity.ZBook;
import com.zenika.zbooks.gwt.client.entity.ZenikaCollection;
import com.zenika.zbooks.gwt.client.events.ZBookAddedEvent;

public class AddZBookPanel extends Composite {
	
	interface MyUiBinder extends UiBinder<Widget, AddZBookPanel> {}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private ZBookRpcServiceAsync zBookGetter = GWT.create(ZBookRpcService.class);

	@UiField ListBox collection;
	@UiField TextBox ISBN;

	public AddZBookPanel () {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("add")
	void handleClick(ClickEvent e) {
		String collectionString = collection.getValue(collection.getSelectedIndex());
		ZenikaCollection collectionEnum = ZenikaCollection.valueOf(collectionString);
		
		AsyncCallback<ZBook> callback = new AsyncCallback<ZBook>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Il y a eu un problème lors de la connexion avec le serveur");
			}

			@Override
			public void onSuccess(ZBook result) {
				AppUtils.EVENT_BUS.fireEvent(new ZBookAddedEvent(result));
			}
		}; 
		this.zBookGetter.addZBook(Long.parseLong(ISBN.getText()), collectionEnum, callback);
		
		Window.Location.replace(Window.Location.getPath() + Window.Location.getQueryString() + "#");
	}
}
