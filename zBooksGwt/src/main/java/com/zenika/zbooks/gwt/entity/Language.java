package com.zenika.zbooks.gwt.entity;


public enum Language {
	FR, EN, ES;
	
	public static Language getValueOfToUpperCase (String value) {
		return valueOf(value.toUpperCase());
	}
}
