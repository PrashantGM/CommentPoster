package com.example.commentposter.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.commentposter.entity.Comment;

import java.util.List;

@Dao
public interface CommentDAO {
    @Insert
    void postComment(Comment comment);

    @Query("SELECT u.name as username,c.id,c.comment,c.date_posted,c.userid FROM users u INNER JOIN comments c ON u.id=c.userid")
    List<Comment> getAllComments();

    @Query("SELECT * FROM comments where userid=(:userid)")
    List<Comment> getCommentsByUser(Integer userid);

    @Query("UPDATE comments SET comment=:comment WHERE id=(:id) AND userid=(:userid)")
    void updateComment(String comment,Integer id,Integer userid);

    @Query("DELETE FROM comments WHERE id=:id AND userid=:userid")
    void deleteComment(Integer id,Integer userid);

}
