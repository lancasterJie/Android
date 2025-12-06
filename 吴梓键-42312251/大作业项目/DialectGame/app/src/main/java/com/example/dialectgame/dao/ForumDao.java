// ForumDao.java
package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dialectgame.model.Comment;
import com.example.dialectgame.model.ForumPost;
import java.util.List;

@Dao
public interface ForumDao {
    // 帖子相关操作
    @Insert
    void insertPost(ForumPost post);

    @Query("SELECT * FROM forum_posts ORDER BY createTime DESC")
    List<ForumPost> getAllPosts();

    @Query("SELECT * FROM forum_posts WHERE id = :postId LIMIT 1")
    ForumPost getPostById(int postId);

    @Update
    void updatePost(ForumPost post);

    @Query("DELETE FROM forum_posts WHERE id = :postId")
    void deletePost(int postId);

    // 评论相关操作
    @Insert
    void insertComment(Comment comment);

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY createTime ASC")
    List<Comment> getCommentsByPostId(int postId);

    @Query("DELETE FROM comments WHERE id = :commentId")
    void deleteComment(int commentId);
}
