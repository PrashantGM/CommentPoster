package com.example.commentposter.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.commentposter.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void registerUser(User userEntity);

    @Query("SELECT * FROM users WHERE email=(:email) AND password=(:password)")
    User login(String email, String password);

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email=(:email))")
    Boolean does_exist(String email);

    @Query("SELECT * FROM users WHERE email=(:email)")
    User getUserData(String email);


    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("UPDATE users SET name=:name,password=:password,dateUpdated=:dateUpdated WHERE email=(:email)")
    void updateUser(String name,String password,String dateUpdated,String email);

    @Query("DELETE FROM users WHERE email=:email")
    void deleteUser(String email);
}
