package com.example.commentposter;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.commentposter.dao.CommentDAO;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.Comment;
import com.example.commentposter.entity.User;

@Database(entities = {User.class, Comment.class},version = 1)
public abstract class CommentPosterDB extends RoomDatabase {

    private static final String dbName="commentPosterDB";
    private static CommentPosterDB commentPosterDB;

    public static synchronized CommentPosterDB getCommentPosterDB(Context context) {

        if (commentPosterDB == null) {
            commentPosterDB = Room.databaseBuilder(context, CommentPosterDB.class,dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return commentPosterDB;
    }

    public abstract UserDAO userDAO();
    public abstract CommentDAO commentDAO();
}
