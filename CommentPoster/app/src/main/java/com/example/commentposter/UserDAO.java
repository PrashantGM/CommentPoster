package com.example.commentposter;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE email=(:email) AND password=(:password)")
    UserEntity login(String email,String password);

    @Query("SELECT * FROM users WHERE email=(:email)")
    UserEntity getUserData(String email);


    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("DELETE FROM users WHERE email=:email")
    void deleteUser(String email);
}
