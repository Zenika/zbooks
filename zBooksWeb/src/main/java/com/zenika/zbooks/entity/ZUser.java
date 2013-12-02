package com.zenika.zbooks.entity;

public class ZUser {

	private int id;
	private String userName;
	private String password;
	private ZPower zPower;
	
	public ZUser () {
		zPower=ZPower.USER;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public ZPower getZPower() {
		return zPower;
	}

	public void setZPower(ZPower zPower) {
		this.zPower = zPower;
	}
	
	
}
