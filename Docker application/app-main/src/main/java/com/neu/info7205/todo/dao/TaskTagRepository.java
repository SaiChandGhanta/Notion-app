package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Task;
import com.neu.info7205.todo.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, Integer> {
    List<TaskTag> findByTagId(int tagId);
    List<TaskTag> findByTaskId(int taskId);
}
