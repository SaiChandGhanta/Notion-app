package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.TaskRepository;
import com.neu.info7205.todo.dao.TaskTagRepository;
import com.neu.info7205.todo.model.State;
import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.TaskTag;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskTagRepository taskTagRepository;

    @Override
    public Task findById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> findAll(int listId) {
        return taskRepository.findByListId(listId);
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task saveTask(Task task) {
        checkAndSetState(task);
        task.setLastModified(new Date());
        task.setCreated(new Date());
        return taskRepository.save(task);
    }
    @Override
    public Task updateTask(Task task) {
        Task task1 = findById(task.getId());
        if(task1!=null){
            checkAndSetState(task);
            task.setCreated(task1.getCreated());
            task.setLastModified(new Date());
            return taskRepository.save(task);
        }
        else{
            throw new SystemException("Task doesn't exist", ErrorCode.INVALID_ID);
        }
    }

    @Override
    public void deleteTaskTag(int taskId) {
        taskTagRepository.deleteAll(getTagsByTask(taskId));
    }

    @Override
    public Task changeList(int listId, Task task){
        if(findById(task.getId())!=null){
            task.setLastModified(new Date());
            task.setListId(listId);
            return taskRepository.save(task);
        }
        else {
            throw new SystemException("Task not found to update", ErrorCode.INVALID_REQUEST);
        }
    }

    @Override
    public TaskTag saveTaskTag(TaskTag taskTag) {
        if(getTagsByTask(taskTag.getTaskId()).size() < 10){
            return taskTagRepository.save(taskTag);
        }else{
            throw new SystemException("Tags Limit exceeded", ErrorCode.INVALID_REQUEST);
        }
    }

    @Override
    public List<TaskTag> getTasksByTag(int tagId) {
        return taskTagRepository.findByTagId(tagId);
    }
    @Override
    public List<TaskTag> getTagsByTask(int taskId) {
        return taskTagRepository.findByTaskId(taskId);
    }

    void checkAndSetState(Task task) {
        if (Objects.isNull(task.getState()) || task.getState() != State.COMPLETE) {
            if (task.getDueDate().before(new Date())) {
                task.setState(State.OVERDUE);
            } else {
                task.setState(State.TODO);
            }
        }
    }

//    public boolean checkIfTaskExistsForUser(int userId, )
}
