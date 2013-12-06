package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.annotations.Param;

public interface ZUserMapper {

    void addZUser(ZUser user);

    void deleteZUser(int id);

    ZUser getZUser(
            @Param("email") String email,
            @Param("password") String password);

    ZUser getZUserWithEmail(String email);
}
