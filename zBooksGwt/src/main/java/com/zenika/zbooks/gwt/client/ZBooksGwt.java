package com.zenika.zbooks.gwt.client;

import java.util.List;

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
	private ZBookRpcServiceAsync zBookGetter = GWT.create(ZBookRpcService.class);
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.initializeZBooksTable();

		mainPanel.add(zBooksTable, DockPanel.CENTER);
		
		RootPanel.get().add(mainPanel);
		
		AsyncCallback<List<ZBook>> callBack = new AsyncCallback<List<ZBook>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<ZBook> result) {
				// TODO Auto-generated method stub
				addZBooksToList(result);
			}
		};
		
		this.zBookGetter.getAllZBooks(callBack);
	}

	private void initializeZBooksTable () {
		zBooksTable.setText(0, 0, "ISBN");
		zBooksTable.setText(0, 1, "Title");
		zBooksTable.setText(0, 2, "Edition");
		zBooksTable.setText(0, 3, "Number of pages");
	}
	
	private void addZBooksToList(List<ZBook> list) {
		for(ZBook zBook : list) {
			this.addZBookToList(zBook);
		}
	}
	
	private void addZBookToList(ZBook zBook) {
		int row = zBooksTable.getRowCount();
		zBooksTable.setText(row, 0, "" + zBook.getISBN());
		zBooksTable.setText(row, 1, zBook.getTitle());
		zBooksTable.setText(row, 2, zBook.getEdition());
		zBooksTable.setText(row, 3, "" + zBook.getPagesNumber());
	}
}
