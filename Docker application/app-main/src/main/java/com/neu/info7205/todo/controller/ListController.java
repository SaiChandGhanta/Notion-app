package com.neu.info7205.todo.controller;


import com.neu.info7205.todo.model.TodoList;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.ListService;
import com.neu.info7205.todo.service.UserService;
import com.neu.info7205.todo.service.UserServiceImpl;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.ResponseObj;
import com.neu.info7205.todo.util.TodoPrecondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ListController {

    @Autowired
    ListService listService;

    @Autowired
    UserService userService;


    @PostMapping(value = "/list")
    public ResponseEntity saveList(@RequestBody TodoList list) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotNull(list.getName(), ErrorCode.LIST_NAME_EMPTY);
        list.setUserId(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(listService.save(list));
    }

    @PutMapping(value = "/list/{listId}")
    public ResponseEntity updateList(@PathVariable String listId, @RequestBody TodoList list) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(listId, ErrorCode.ID_REQUIRED);
        TodoPrecondition.assertNotNull(list.getName(), ErrorCode.LIST_NAME_EMPTY);
        list.setUserId(user.getId());
        list.setId(Integer.parseInt(listId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listService.update(list));
    }

    @GetMapping(value = "/list/{listId}")
    public ResponseEntity getList(@PathVariable int listId) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        TodoPrecondition.assertNotBlank(String.valueOf(listId), ErrorCode.ID_REQUIRED);
        return ResponseEntity.ok(listService.findListByUserListId(user.getId(), listId));
    }

    @GetMapping(value = "/lists")
    public ResponseEntity getLists() {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        return ResponseEntity.ok(listService.findAll(user.getId()));
    }


}