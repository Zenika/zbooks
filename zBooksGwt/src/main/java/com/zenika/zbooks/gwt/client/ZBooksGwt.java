package com.zenika.zbooks.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.zbooks.gwt.client.entity.ZBook;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ZBooksGwt implements EntryPoint {

	private final FlexTable zBooksTable= new FlexTable();
	private final DockPanel mainPanel = new DockPanel();
	private ZBookGetterServiceAsync zBookGetter = GWT.create(ZBookGetterService.class);
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.initializeZBooksTable();

		mainPanel.add(zBooksTable, DockPanel.CENTER);
		
		RootPanel.get().add(mainPanel);
		
		AsyncCallback<ZBook> callBack = new AsyncCallback<ZBook>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ZBook result) {
				// TODO Auto-generated method stub
				addZBookToList(result);
			}
		};
		
		this.zBookGetter.getZBook(42, callBack);
	}

	private void initializeZBooksTable () {
		zBooksTable.setText(0, 0, "ISBN");
		zBooksTable.setText(0, 1, "Title");
		zBooksTable.setText(0, 2, "Edition");
		zBooksTable.setText(0, 3, "Number of pages");
	}
	
	private void addZBookToList(ZBook zBook) {
		int row = zBooksTable.getRowCount();
		zBooksTable.setText(row, 0, "" + zBook.getISBN());
		zBooksTable.setText(row, 1, zBook.getTitle());
		zBooksTable.setText(row, 2, zBook.getEdition());
		zBooksTable.setText(row, 3, "" + zBook.getPagesNumber());
	}
}
