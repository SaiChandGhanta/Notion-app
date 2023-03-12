package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Attachment;
import com.neu.info7205.todo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    List<Attachment> findByTaskId(int taskId);
}
