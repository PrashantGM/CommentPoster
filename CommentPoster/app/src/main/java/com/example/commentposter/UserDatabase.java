package com.example.commentposter;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String dbName="user";
    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context) {

        if (userDatabase == null) {
            userDatabase= Room.databaseBuilder(context,UserDatabase.class,dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return userDatabase;
    }

    public abstract UserDAO userDAO();
}
