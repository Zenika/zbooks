package com.zenika.zbooks.gwt.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.zbooks.gwt.client.entity.Author;
import com.zenika.zbooks.gwt.client.entity.ZBook;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ZBooksGwt implements EntryPoint {

	private final FlexTable zBooksTable= new FlexTable();
	private final DockPanel mainPanel = new DockPanel();
	private final Label label = new Label ("You're on the page to see the library");
	private ZBookRpcServiceAsync zBookGetter = GWT.create(ZBookRpcService.class);
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.initializeZBooksTable();

		Anchor anchor = new Anchor("Test ajout", "#add");
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue().contains("add")) {
					label.setText("You're on the page to add a book : " +event.getValue());
				} else {
					label.setText("You're on the page to see the library" +event.getValue());
				}
			}
		});
		mainPanel.add(anchor, DockPanel.NORTH);
		
		mainPanel.add(zBooksTable, DockPanel.CENTER);
		mainPanel.add(label, DockPanel.SOUTH);
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
		zBooksTable.setText(0, 1, "Titre");
		zBooksTable.setText(0, 2, "Edition");
		zBooksTable.setText(0, 3, "Auteurs");
		zBooksTable.setText(0, 4, "Nombre de pages");
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
		StringBuilder sb = new StringBuilder();
		List<Author> authors = zBook.getAuthors();
		for (int i=0; i<authors.size(); i++) {
			Author author = authors.get(i);
			sb.append(author.getFirstName());
			sb.append(" ");
			sb.append(author.getLastName());
			if (i<authors.size() - 1) {
				sb.append(", ");
			}
		}
		zBooksTable.setText(row, 3, sb.toString());
		zBooksTable.setText(row, 4, "" + zBook.getPagesNumber());
	}
}
