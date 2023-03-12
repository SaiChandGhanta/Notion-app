package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Tag;
import com.neu.info7205.todo.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListRepository extends JpaRepository<TodoList, Integer> {
    List<TodoList> findByUserId(int userId);
    @Query( "select l from TodoList l where l.userId =:userId and l.name =:listName" )
    TodoList findListNameByUser(@Param("userId")int userId, @Param("listName")String listName);
    @Query( "select l from TodoList l where l.userId =:userId and l.id =:listId" )
    TodoList findListByUserListId(@Param("userId")int userId, @Param("listId")int listId);
}
