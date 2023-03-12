package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.Attachment;

import java.util.List;

public interface AttachmentService {
    Attachment findById(int id);

    List<Attachment> findAll(int taskId);

    void deleteAttachment(int id);

    Attachment save(Attachment attachment);
    void deleteAttachementsForTask(int taskId);
}
