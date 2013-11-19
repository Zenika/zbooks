package com.zenika.zbooks.gwt.entity;


public enum ZenikaCollection {
	SBR, 
	RENNES, 
	NANTES;
	
	public static ZenikaCollection getValueOfToUpperCase (String value) {
		return valueOf(value.toUpperCase());
	}
}
