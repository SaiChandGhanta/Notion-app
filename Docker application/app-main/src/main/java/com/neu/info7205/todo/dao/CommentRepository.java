package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query( "select c from Comment c where c.taskId =:taskId" )
    List<Comment> findAllCommentsByTaskId(@Param("taskId")int taskId);
}
