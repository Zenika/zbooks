package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZUserMapper {

    void addZUser(ZUser user);

    void deleteZUser(int id);

    ZUser getZUser(
            @Param("email") String email,
            @Param("password") String password);

    ZUser getZUserWithEmail(String email);

    ZUser getUserWithProfile(@Param("user_id") int id);
}
