package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.ListRepository;
import com.neu.info7205.todo.model.TodoList;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.SystemException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListServiceImpl implements ListService{

    @Autowired
    ListRepository listRepository;

    @Override
    public TodoList findById(int id) {
        return listRepository.findById(id).orElse(null);
    }

    @Override
    public TodoList save(TodoList list) {
        if(listRepository.findListNameByUser(list.getUserId(), list.getName()) == null){
            list.setLastModified(new Date());
            list.setCreated(new Date());
            return listRepository.save(list);
        } else {
            throw new SystemException("List already exists", ErrorCode.INVALID_REQUEST);
        }
    }

    @Override
    public TodoList update(TodoList list) {
        TodoList list1 = findListByUserListId(list.getUserId(), list.getId());
        if(list1!= null){
            list1.setName(list.getName());
            list1.setLastModified(new Date());
            return listRepository.save(list1);
        } else {
            throw new SystemException("List already exists", ErrorCode.INVALID_REQUEST);
        }
    }
    @Override
    public List<TodoList> findAll(int userId) {
        return listRepository.findByUserId(userId);
    }

    @Override
    public TodoList findListByUserListId(int userId, int listId){
        return listRepository.findListByUserListId(userId, listId);
    }
}
