package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.CommentRepository;
import com.neu.info7205.todo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Comment addComment(Comment comment){
        //check if task exists
        comment.setComment_modified(new Date());
        comment.setComment_created(new Date());
        return commentRepository.save(comment);
    }
    @Override
    public List<Comment> getCommentsByTaskId(int taskId){
        return commentRepository.findAllCommentsByTaskId(taskId);
    }
    @Override
    public void deleteCommentsForTask(int taskId) {
        commentRepository.deleteAll(getAllCommentsByTask(taskId));
    }
    public List<Comment> getAllCommentsByTask(int taskId){
        return commentRepository.findAllCommentsByTaskId(taskId);
    }

}
