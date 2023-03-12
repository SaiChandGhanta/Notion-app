package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.TaskTag;

import java.util.List;

public interface TaskService {
    Task findById(int id);
    List<Task> findAll(int listId);
    void deleteTask(int id);
    Task saveTask(Task task);
    Task changeList(int listId, Task task);
    TaskTag saveTaskTag(TaskTag taskTag);
    List<TaskTag> getTasksByTag(int tagId);
    Task updateTask(Task task);
    void deleteTaskTag(int taskId);
    List<TaskTag> getTagsByTask(int taskId);
}
