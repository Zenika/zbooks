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
    
    /**
     * Method to update the DB to borrow or return a zBook. Set the idBorrower to 0 if you wish to return it.
     * @param book_id
     * @param idBorrower
     */
    void borrowOrReturnBook (
    		@Param("book_id") int book_id,
            @Param("idBorrower") int idBorrower);

    ZUser getUserWithProfile(@Param("user_id") int id);
}
