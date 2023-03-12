package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.model.Comment;
import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.CommentService;
import com.neu.info7205.todo.service.ListService;
import com.neu.info7205.todo.service.TaskService;
import com.neu.info7205.todo.service.UserServiceImpl;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.TodoPrecondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ListService listService;
    @Autowired
    TaskService taskService;

    //how to check that a particular is accessing their tasks and comments?
    @PostMapping("/task/comment")
    public ResponseEntity addComment(@Valid @RequestBody Comment comment) {
        TodoPrecondition.assertNotNull(String.valueOf(comment.getTaskId()), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(comment.getComment_created(), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(comment.getComment_modified(), ErrorCode.INVALID_REQUEST_BODY);
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(comment.getTaskId());
        TodoPrecondition.assertNotNull(task, ErrorCode.NO_PERMISSION);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.NO_PERMISSION);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(comment));

        //check if user is tagged to the list and if yes proceed
    }

    @GetMapping("/task/comments")
    public ResponseEntity getCommentsByTaskId(@Valid @RequestParam int taskId) {
        //check if user is tagged to the list and proceed if he is
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(taskId);
        TodoPrecondition.assertNotNull(task, ErrorCode.NO_PERMISSION);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByTaskId(taskId));

    }

    @DeleteMapping("/task/comments")
    public ResponseEntity deleteCommentsByTaskId(@Valid @RequestParam int taskId) {
        //check if user is tagged to the list and proceed if he is
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(taskId);
        TodoPrecondition.assertNotNull(task, ErrorCode.NO_PERMISSION);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.NO_PERMISSION);
        commentService.deleteCommentsForTask(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
}
