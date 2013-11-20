package com.zenika.zbooks.gwt.client.entity;


public enum Language {
	FR, EN, ES;
	
	public static Language getValueOfToUpperCase (String value) {
		return valueOf(value.toUpperCase());
	}
}
