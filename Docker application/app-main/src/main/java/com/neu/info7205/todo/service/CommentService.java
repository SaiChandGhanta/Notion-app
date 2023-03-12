package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.Comment;

import java.util.List;

public interface CommentService {
    public Comment addComment(Comment comment);
    public List<Comment> getCommentsByTaskId(int taskId);
    public void deleteCommentsForTask(int taskId);
}
