package com.example.commentposter.entity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "comments",foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userid",
        onDelete = CASCADE))
public class Comment {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "date_posted")
    public String date_posted;

    @ColumnInfo(name="username")
    public String username;

    @ColumnInfo(name="userid")
    public Integer userid;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
