package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.model.Attachment;
import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.AttachmentService;
import com.neu.info7205.todo.service.ListService;
import com.neu.info7205.todo.service.TaskService;
import com.neu.info7205.todo.service.UserService;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.TodoPrecondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    ListService listService;

    @PostMapping(value = "/task/attachment")
    public ResponseEntity saveAttachment(@RequestBody Attachment attachment) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(String.valueOf(attachment.getTaskId()), ErrorCode.TASK_ID_EMPTY);
        TodoPrecondition.assertNotBlank(String.valueOf(attachment.getListId()), ErrorCode.LIST_ID_EMPTY);
        Task task = taskService.findById(attachment.getTaskId());
        TodoPrecondition.assertNotNull(task, ErrorCode.NO_PERMISSION);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), attachment.getListId()), ErrorCode.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.save(attachment));

    }

    @PutMapping(value = "/task/attachment/{attachmentId}")
    public ResponseEntity updateAttachment(@PathVariable String attachmentId, @RequestBody Attachment attachment) {
        TodoPrecondition.assertNotNull(userService.getLoggedInUser(), ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(attachmentId, ErrorCode.ID_REQUIRED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(attachmentService.save(attachment));
    }


    @DeleteMapping(value = "/task/attachment/{attachmentId}")
    public ResponseEntity delete(@PathVariable String attachmentId) {
        TodoPrecondition.assertNotNull(userService.getLoggedInUser(), ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(attachmentId, ErrorCode.ID_REQUIRED);
        attachmentService.deleteAttachment(Integer.parseInt(attachmentId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    @GetMapping(value = "/task/{taskId}/attachments")
    public ResponseEntity getAllAttachmentsByTask(@PathVariable int taskId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
//        Attachment attachment = attachmentService.findById(attachmentId);
//        TodoPrecondition.assertNotNull(attachment, ErrorCode.ID_REQUIRED);
        Task task = taskService.findById(taskId);
        TodoPrecondition.assertNotNull(task, ErrorCode.TASK_DOESNT_EXIST);
        TodoPrecondition.assertNotNull(listService.findListByUserListId(user.getId(), task.getListId()), ErrorCode.LIST_EMPTY);
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.findAll(taskId));

    }
}
