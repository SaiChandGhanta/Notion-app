package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Comment;
import com.neu.info7205.todo.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
    @Query( "select r from Reminder r where r.taskId =:taskId" )
    List<Reminder> findAllRemindersByTaskId(@Param("taskId")int taskId);
}
