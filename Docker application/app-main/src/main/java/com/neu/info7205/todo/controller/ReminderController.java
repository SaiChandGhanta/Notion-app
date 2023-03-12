package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.model.Reminder;
import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.*;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.ResponseObj;
import com.neu.info7205.todo.util.SystemException;
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
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ListService listService;
    @Autowired
    TaskService taskService;
    @PostMapping("/task/reminder")
    public ResponseEntity<Reminder> addReminder(@Valid @RequestBody Reminder reminder){
        TodoPrecondition.assertNotNull(String.valueOf(reminder.getTaskId()), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(reminder.getReminder_created(), ErrorCode.INVALID_REQUEST_BODY);
        TodoPrecondition.assertNull(reminder.getReminder_modified(), ErrorCode.INVALID_REQUEST_BODY);
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(reminder.getTaskId());
        if(task!=null){
            if(listService.findListByUserListId(user.getId(), task.getListId())!=null){
                TodoPrecondition.assertNotNull(reminder.getReminderDate(), ErrorCode.INVALID_REQUEST_BODY);
                return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.addReminder(reminder));
            }
            else{
                throw new SystemException("List not found for particular user", ErrorCode.UNAUTHORIZED_USER);
            }
        }
        else{
            throw new SystemException("Task not found", ErrorCode.INVALID_REQUEST);
        }
        //check if user is tagged to the list and if yes proceed
    }

    @GetMapping("/task/reminders")
    public ResponseEntity getRemindersByTaskId(@Valid @RequestParam int taskId){
        //check if user is tagged to the list and proceed if he is
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(taskId);
        if(task!=null){
            if(listService.findListByUserListId(user.getId(), task.getListId())!=null){
                return ResponseEntity.ok(reminderService.getAllReminders(taskId));
            }
            else{
                throw new SystemException("List not found for particular user", ErrorCode.UNAUTHORIZED_USER);
            }
        }
        else{
            throw new SystemException("Task not found", ErrorCode.TASK_DOESNT_EXIST);
        }
    }

    @DeleteMapping("/task/reminders")
    public ResponseEntity deleteRemindersByTaskId(@Valid @RequestParam int taskId){
        //check if user is tagged to the list and proceed if he is
        User user = userService.getLoggedInUser();
        Task task = taskService.findById(taskId);
        if(task!=null){
            if(listService.findListByUserListId(user.getId(), task.getListId())!=null){
                reminderService.deleteRemindersForTask(taskId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            else{
                throw new SystemException("List not found for particular user", ErrorCode.UNAUTHORIZED_USER);
            }
        }
        else{
            throw new SystemException("Task not found", ErrorCode.INVALID_REQUEST);
        }
    }
}

