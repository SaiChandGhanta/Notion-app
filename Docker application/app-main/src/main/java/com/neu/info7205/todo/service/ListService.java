package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.TodoList;

import java.util.List;

public interface ListService {
    TodoList findById(int id);
    TodoList save(TodoList list);
    List<TodoList> findAll(int userId);
    public TodoList update(TodoList list);
    public TodoList findListByUserListId(int userId, int listId);
}
