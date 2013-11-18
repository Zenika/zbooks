package com.zenika.zbooks.entity;

public enum ZenikaCollection {
	SBR, 
	RENNES, 
	NANTES;
	
	public static ZenikaCollection getValueOfToUpperCase (String value) {
		return valueOf(value.toUpperCase());
	}
}
