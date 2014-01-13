package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
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

    /**
     * Method to update the DB to borrow a zBook.
     * @param book
     * @param user
     */
    void borrowBook (
            @Param("book") ZBook book,
            @Param("user") ZUser user);

    /**
     * Method to update the DB to borrow or return a zBook. Set the idBorrower to 0 if you wish to return it.
     * @param book
     */
    void returnBook (@Param("book_id") ZBook book);

    ZUser getUserWithProfile(@Param("user_id") int id);
}
