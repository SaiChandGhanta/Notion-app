package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.model.Tag;
import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.TaskTag;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.*;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.TodoPrecondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    ListService listService;
    @Autowired
    TagService tagService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ReminderService reminderService;
    @Autowired
    CommentService commentService;

    @PostMapping(value = "/task")
    public ResponseEntity saveTask(@RequestBody Task task) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(task.getListId(), ErrorCode.LIST_ID_EMPTY);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        TodoPrecondition.assertNotNull(task.getSummary(), ErrorCode.TASK_SUMMARY_EMPTY);
        TodoPrecondition.assertNotNull(task.getPriority(), ErrorCode.TASK_PRIORITY_EMPTY);
        TodoPrecondition.assertNull(task.getCreated(), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(task.getLastModified(), ErrorCode.INVALID_REQUEST_BODY);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(task));
    }

    @PutMapping(value = "/task/{taskId}")
    public ResponseEntity updateTask(@PathVariable int taskId, @RequestBody Task task) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(String.valueOf(taskId), ErrorCode.TASK_ID_EMPTY);
        TodoPrecondition.assertNotNull(task.getListId(), ErrorCode.LIST_ID_EMPTY);
        TodoPrecondition.assertNotNull(taskService.findById(taskId), ErrorCode.TASK_DOESNT_EXIST);
        TodoPrecondition.assertNotNull(task.getSummary(), ErrorCode.TASK_SUMMARY_EMPTY);
        TodoPrecondition.assertNotNull(task.getPriority(), ErrorCode.TASK_PRIORITY_EMPTY);
        TodoPrecondition.assertNull(task.getCreated(), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(task.getLastModified(), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskService.updateTask(task));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity getTaskByTaskId(@PathVariable int taskId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(String.valueOf(taskId), ErrorCode.ID_REQUIRED);
        Task task = taskService.findById(taskId);
        TodoPrecondition.assertNotNull(task, ErrorCode.TASK_DOESNT_EXIST);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping(value = "/task/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable int taskId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotBlank(String.valueOf(taskId), ErrorCode.ID_REQUIRED);
        Task task = taskService.findById(taskId);
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(task, ErrorCode.TASK_DOESNT_EXIST);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        taskService.deleteTaskTag(taskId);
        attachmentService.deleteAttachementsForTask(taskId);
        reminderService.deleteRemindersForTask(taskId);
        commentService.deleteCommentsForTask(taskId);
        taskService.deleteTask(taskId);
        //delete comments, reminders, attachments
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    @GetMapping(value = "/{listId}/tasks")
    public ResponseEntity getAll(@PathVariable int listId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), listId), ErrorCode.LIST_EMPTY);
        return ResponseEntity.ok(taskService.findAll(listId));
    }

    @PutMapping(value = "/task/list/{newListId}")
    public ResponseEntity moveTaskToList(@PathVariable("newListId") int newListId, @RequestBody Task task) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(userService.getLoggedInUser(), ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskService.changeList(newListId, task));
    }

    @PostMapping(value = "/task/tag")
    public ResponseEntity addTagToTask(@RequestBody Map<Object, String> body) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(body.get("taskId").toString(), ErrorCode.TASK_ID_EMPTY);
        TodoPrecondition.assertNotNull(body.get("tagId").toString(), ErrorCode.TAG_ID_EMPTY);
        Task task = taskService.findById(Integer.parseInt(body.get("taskId")));
        Tag tag = tagService.findTagByUserId(user.getId(), Integer.parseInt(body.get("tagId")));
        TodoPrecondition.assertNotNull(tag, ErrorCode.NO_PERMISSION);
        TodoPrecondition.assertNotNull(task, ErrorCode.NO_PERMISSION);

        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.NO_PERMISSION);
        TaskTag taskTag = new TaskTag();
        taskTag.setTagId(tag.getId());
        taskTag.setTaskId(task.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTaskTag(taskTag));

    }

    @GetMapping("/task/tag/{tagId}")
    public ResponseEntity getTasksByTag(@PathVariable int tagId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        Tag tag = tagService.findTagByUserId(user.getId(), tagId);
        TodoPrecondition.assertNotNull(tag, ErrorCode.TAG_DOES_NOT_EXIST);
        List<TaskTag> tasks = taskService.getTasksByTag(tagId);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/search/{name}")
    public ResponseEntity getTasks(@PathVariable String name) {

        TodoPrecondition.assertNotBlank(name, ErrorCode.NAME_EMPTY);
        return new ResponseEntity( HttpStatus.NOT_IMPLEMENTED);
    }
}
