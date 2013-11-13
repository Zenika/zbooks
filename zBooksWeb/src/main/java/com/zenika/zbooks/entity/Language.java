package com.zenika.zbooks.entity;

public enum Language {
	FR("fr"), EN("en"), ES("es");
	
	private String value;
	
	Language (String value) {
		this.value=value;
	}
	
	public String getValue () {
		return this.value;
	}

}
