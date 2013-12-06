package com.zenika.zbooks.persistence;

import org.apache.ibatis.annotations.Param;

import com.zenika.zbooks.entity.ZUser;

public interface ZUserMapper {

	void addZUser (ZUser user);
	
	void deleteZUser (int id);
	
	ZUser getZUser (
			@Param("email") String email, 
			@Param("password") String password);
	
	ZUser getZUserWithEmail(String email);
}
