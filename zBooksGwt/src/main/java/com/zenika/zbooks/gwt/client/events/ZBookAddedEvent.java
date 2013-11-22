package com.zenika.zbooks.gwt.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.zenika.zbooks.gwt.client.entity.ZBook;

public class ZBookAddedEvent extends GwtEvent<ZBookAddedEventHandler> {

	public static Type<ZBookAddedEventHandler> TYPE = new Type<ZBookAddedEventHandler>();
	
	private ZBook newZBook;
	
	public ZBookAddedEvent (ZBook zBook) {
		this.newZBook = zBook;
	}
	
	public ZBook getNewZBook () {
		return this.newZBook;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ZBookAddedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ZBookAddedEventHandler handler) {
		handler.onZBookAdded(this);
	}

}
